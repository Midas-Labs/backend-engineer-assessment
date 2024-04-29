package com.midas.app.workflows;

import com.midas.app.activities.UpdateAccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import jakarta.transaction.Transactional;
import java.time.Duration;
import org.slf4j.Logger;

@WorkflowImpl(taskQueues = UpdateAccountWorkflow.QUEUE_NAME)
public class UpdateAccountWorkflowImpl implements UpdateAccountWorkflow {
  private final Logger log = Workflow.getLogger(UpdateAccountWorkflowImpl.class);
  private final UpdateAccountActivity updateAccountActivity;

  public UpdateAccountWorkflowImpl() {
    this.updateAccountActivity =
        Workflow.newActivityStub(UpdateAccountActivity.class, getOptions());
  }

  private ActivityOptions getOptions() {
    return ActivityOptions.newBuilder()
        .setStartToCloseTimeout(Duration.ofSeconds(10))
        .setRetryOptions(
            RetryOptions.newBuilder()
                .setInitialInterval(Duration.ofSeconds(2))
                .setMaximumAttempts(3)
                .build())
        .build();
  }

  @Override
  @Transactional
  public Account updateAccount(Account details) {
    Account existingAccount = updateAccountActivity.findAccount(details);
    updateAccountActivity.updatePaymentAccount(details);
    Account account = updateAccountActivity.updateAccount(details);
    updateAccountActivity.updatePaymentAccount(details);
    log.info("Account saved: {}", account.getEmail());
    return account;
  }

  private Account updateAccountFields(Account existingAccount, Account newAccount) {
    if (!newAccount.getEmail().isEmpty() && !newAccount.getEmail().isBlank()) {
      existingAccount.setEmail(newAccount.getEmail());
    }
    if (!newAccount.getFirstName().isEmpty() && !newAccount.getFirstName().isBlank())
      existingAccount.setFirstName(newAccount.getFirstName());
    if (!newAccount.getLastName().isEmpty() && !newAccount.getLastName().isBlank())
      existingAccount.setLastName(newAccount.getLastName());
    return existingAccount;
  }
}
