package com.midas.app.activities;

import com.midas.app.mappers.AccountMapper;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.CreateAccountWorkflow;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@ActivityImpl(taskQueues = CreateAccountWorkflow.QUEUE_NAME)
public class CreateAccountActivityImpl implements CreateAccountActivity {

  private final PaymentProvider paymentProvider;
  private final AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    return paymentProvider.createAccount(AccountMapper.toCreateAccount(account));
  }
}
