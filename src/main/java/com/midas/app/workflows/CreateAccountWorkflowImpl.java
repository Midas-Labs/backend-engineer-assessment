package com.midas.app.workflows;

import com.midas.app.activities.CreateAccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import org.slf4j.Logger;

@WorkflowImpl(taskQueues = CreateAccountWorkflow.QUEUE_NAME)
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
  private final Logger log = Workflow.getLogger(CreateAccountWorkflowImpl.class);
  private final CreateAccountActivity createAccountActivity;

  public CreateAccountWorkflowImpl() {
    this.createAccountActivity =
        Workflow.newActivityStub(CreateAccountActivity.class, getOptions());
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
  public Account createAccount(Account details) {
    Account account = createAccountActivity.createPaymentAccount(details);
    account = createAccountActivity.saveAccount(account);
    log.info("Account saved: {}", account.getEmail());
    return account;
  }
}
