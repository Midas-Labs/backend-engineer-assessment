package com.midas.app.activities;

import com.midas.app.enums.PaymentProviderType;
import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.PaymentProvideFactory;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.providers.payment.UpdateAccount;
import com.midas.app.repositories.AccountRepository;
import io.temporal.spring.boot.ActivityImpl;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = {"update-account-workflow", "create-account-workflow"})
public class AccountActivityImpl implements AccountActivity {

  private final AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    PaymentProvider paymentProvider =
        PaymentProvideFactory.getPaymentProvider(PaymentProviderType.Stripe);
    return paymentProvider.createAccount(
        CreateAccount.builder()
            .firstName(account.getFirstName())
            .lastName(account.getLastName())
            .email(account.getEmail())
            .userId(String.valueOf(account.getId()))
            .build());
  }

  @Override
  public Account updatePaymentAccount(Account account) {
    PaymentProvider paymentProvider =
        PaymentProvideFactory.getPaymentProvider(account.getPaymentProviderType());
    return paymentProvider.updateAccount(
        UpdateAccount.builder()
            .firstName(account.getFirstName())
            .lastName(account.getLastName())
            .email(account.getEmail())
            .userId(String.valueOf(account.getId()))
            .providerId(account.getProviderId())
            .build());
  }

  @Override
  public Account getAccount(UUID id) {
    Optional<Account> account = accountRepository.findAccountById(id);
    if (account.isPresent()) {
      return account.get();
    } else {
      throw new RuntimeException("Account not found");
    }
  }
}
