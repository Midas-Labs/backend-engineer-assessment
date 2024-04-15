package com.midas.app.services;

import com.midas.app.models.Account;
import java.util.List;

public interface AccountService {
  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(Account details);

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  List<Account> getAccounts();

  /**
   * getAccountById returns an account by its id.
   *
   * @param details is the details of the account found.
   * @return Account
   */
  Account updateAccount(Account details);
}
