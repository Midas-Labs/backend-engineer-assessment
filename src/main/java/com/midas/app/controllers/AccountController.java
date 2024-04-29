package com.midas.app.controllers;

import com.midas.app.mappers.AccountMapper;
import com.midas.app.services.AccountService;
import com.midas.generated.api.AccountsApi;
import com.midas.generated.model.AccountDto;
import com.midas.generated.model.CreateAccountDto;
import com.midas.generated.model.UpdateAccountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AccountController implements AccountsApi {
  private final AccountService accountService;
  private final Logger logger = LoggerFactory.getLogger(AccountController.class);

  /**
   * POST /accounts : Create a new user account Creates a new user account with the given details
   * and attaches a supported payment provider such as &#39;stripe&#39;.
   *
   * @param createAccountDto User account details (required)
   * @return User account created (status code 201)
   */
  @Override
  public ResponseEntity<AccountDto> createUserAccount(CreateAccountDto createAccountDto) {
    logger.info("Creating account for user with email: {}", createAccountDto.getEmail());

    var account = accountService.createAccount(AccountMapper.toAccount(createAccountDto));

    return new ResponseEntity<>(AccountMapper.toAccountDto(account), HttpStatus.CREATED);
  }

  /**
   * GET /accounts : Get list of user accounts Returns a list of user accounts.
   *
   * @return List of user accounts (status code 200)
   */
  @Override
  public ResponseEntity<List<AccountDto>> getUserAccounts() {
    logger.info("Retrieving all accounts");

    var accounts = accountService.getAccounts();
    var accountsDto = accounts.stream().map(AccountMapper::toAccountDto).toList();

    return new ResponseEntity<>(accountsDto, HttpStatus.OK);
  }

  /**
   * PATCH /accounts/{accountId} : Update user account Updates the user account with the given
   * details.
   *
   * @param accountId Account ID (required)
   * @param updateAccountDto User account details (required)
   * @return User account updated (status code 200)
   */
  @Override
  public ResponseEntity<AccountDto> updateUserAccount(
      String accountId, UpdateAccountDto updateAccountDto) {
    logger.info("Updating account with ID: {}", accountId);

    var account =
        accountService.updateAccount(AccountMapper.toAccount(accountId, updateAccountDto));

    return new ResponseEntity<>(AccountMapper.toAccountDto(account), HttpStatus.OK);
  }
}
