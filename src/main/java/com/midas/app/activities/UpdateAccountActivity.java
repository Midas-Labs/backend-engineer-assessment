package com.midas.app.activities;

import com.midas.app.models.Account;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UpdateAccountActivity {
  /**
   * findAndUpdateAccount update an existing account in the data store.
   *
   * @param account is the account to be updated
   * @return Account
   */
  @ActivityMethod
  Account updateAccount(Account account);

  /**
   * FindAccount finds an account in the data store.
   *
   * @param account is the account to be found
   * @return Account
   */
  @ActivityMethod
  Account findAccount(Account account);

  /**
   * updatePaymentAccount updates a payment account in the system or provider.
   *
   * @param account is the account to be updates
   * @return Account
   */
  @ActivityMethod
  Account updatePaymentAccount(Account account);
}
