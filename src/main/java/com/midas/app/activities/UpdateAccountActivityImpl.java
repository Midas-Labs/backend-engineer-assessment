package com.midas.app.activities;

import com.midas.app.exceptions.ResourceNotFoundException;
import com.midas.app.mappers.AccountMapper;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.UpdateAccountWorkflow;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@ActivityImpl(taskQueues = UpdateAccountWorkflow.QUEUE_NAME)
public class UpdateAccountActivityImpl implements UpdateAccountActivity {

  private final PaymentProvider paymentProvider;
  private final AccountRepository accountRepository;

  @Override
  public Account updateAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account findAccount(Account account) {
    return accountRepository
            .findById(account.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
  }

  @Override
  public Account updatePaymentAccount(Account account) {
    return paymentProvider.updateAccount(AccountMapper.toUpdateAccount(account));
  }
}
