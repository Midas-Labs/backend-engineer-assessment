package com.midas.app.providers.payment;

import com.midas.app.models.Account;

public interface PaymentProvider {
  /** providerName is the name of the payment provider */
  String providerName();

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(CreateAccount details);

  /**
   * updateAccount updates an account in the payment provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  Account updateAccount(UpdateAccount details);
}
