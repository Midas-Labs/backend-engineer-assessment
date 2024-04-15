package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import com.midas.app.utils.WorkflowUtils;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WorkflowImpl(taskQueues = "create-account-workflow")
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {

  private final AccountActivity activity =
      Workflow.newActivityStub(AccountActivity.class, WorkflowUtils.options);

  @Override
  public Account createAccount(Account details) {
    Account account = activity.saveAccount(details);
    log.info("Account Created Successfully");

    Account paymentAccount = activity.createPaymentAccount(account);
    account.setPaymentProviderType(paymentAccount.getPaymentProviderType());
    account.setProviderId(paymentAccount.getProviderId());

    log.info("Updating Payment Details In Account");
    return activity.saveAccount(account);
  }
}
