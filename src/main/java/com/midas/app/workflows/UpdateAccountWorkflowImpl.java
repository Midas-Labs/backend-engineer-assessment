package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import com.midas.app.utils.WorkflowUtils;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WorkflowImpl(taskQueues = "update-account-workflow")
public class UpdateAccountWorkflowImpl implements UpdateAccountWorkflow {

  private final AccountActivity activity =
      Workflow.newActivityStub(AccountActivity.class, WorkflowUtils.options);

  @Override
  public Account updateAccount(Account details) {
    Account account = activity.getAccount(details.getId());
    account.setFirstName(details.getFirstName());
    account.setLastName(details.getLastName());
    account.setEmail(details.getEmail());

    activity.updatePaymentAccount(account);
    return activity.saveAccount(account);
  }
}
