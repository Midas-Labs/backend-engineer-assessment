package com.midas.app.mappers;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.UpdateAccount;
import com.midas.generated.model.AccountDto;
import com.midas.generated.model.CreateAccountDto;
import com.midas.generated.model.UpdateAccountDto;
import java.util.UUID;
import lombok.NonNull;

public class AccountMapper {
  // Prevent instantiation
  private AccountMapper() {}

  /**
   * toAccountDto maps an account to an account dto.
   *
   * @param account is the account to be mapped
   * @return AccountDto
   */
  public static AccountDto toAccountDto(@NonNull Account account) {
    var accountDto = new AccountDto();

    accountDto
        .id(account.getId())
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .email(account.getEmail())
        .createdAt(account.getCreatedAt())
        .updatedAt(account.getUpdatedAt())
        .providerType(account.getProviderType().name())
        .providerId(account.getProviderId());

    return accountDto;
  }

  /**
   * toAccount maps an account dto to an account.
   *
   * @param createAccountDto is the account dto to be mapped
   * @return Account
   */
  public static Account toAccount(@NonNull CreateAccountDto createAccountDto) {
    return Account.builder()
        .firstName(createAccountDto.getFirstName())
        .lastName(createAccountDto.getLastName())
        .email(createAccountDto.getEmail())
        .build();
  }

  /**
   * toAccount maps an account dto to an account.
   *
   * @param updateAccountDto is the account dto to be mapped
   * @return Account
   */
  public static Account toAccount(
      @NonNull String accountId, @NonNull UpdateAccountDto updateAccountDto) {
    return Account.builder()
        .firstName(updateAccountDto.getFirstName())
        .lastName(updateAccountDto.getLastName())
        .email(updateAccountDto.getEmail())
        .id(UUID.fromString(accountId))
        .build();
  }

  /**
   * toCreateAccount maps an account to a create account.
   *
   * @param account is the account dto to be mapped
   * @return CreateAccountDto
   */
  public static CreateAccount toCreateAccount(@NonNull Account account) {
    return CreateAccount.builder()
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .email(account.getEmail())
        .build();
  }

  /**
   * toUpdateAccount maps an account to an update account.
   *
   * @param account is the account dto to be mapped
   * @return UpdateAccountDto
   */
  public static UpdateAccount toUpdateAccount(@NonNull Account account) {
    return UpdateAccount.builder()
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .email(account.getEmail())
        .userId(account.getProviderId())
        .build();
  }
}
