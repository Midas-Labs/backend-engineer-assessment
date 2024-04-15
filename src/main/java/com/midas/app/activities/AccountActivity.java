package com.midas.app.activities;

import com.midas.app.models.Account;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import java.util.UUID;

@ActivityInterface
public interface AccountActivity {
  /**
   * saveAccount saves an account in the data store.
   *
   * @param account is the account to be saved
   * @return Account
   */
  @ActivityMethod
  Account saveAccount(Account account);

  /**
   * createPaymentAccount creates a payment account in the system or provider.
   *
   * @param account is the account to be created
   * @return Account
   */
  @ActivityMethod
  Account createPaymentAccount(Account account);

  /**
   * updateAccount updates an account in the system or provider.
   *
   * @param account is the account to be updated
   * @return Account
   */
  @ActivityMethod
  Account updatePaymentAccount(Account account);

  /**
   * getAccount returns an account by its id.
   *
   * @param id is the id of the account to be found
   * @return Account
   */
  @ActivityMethod
  Account getAccount(UUID id);
}
