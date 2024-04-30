package com.midas.app.utils;

import com.midas.app.models.Account;
import com.midas.app.models.enums.ProviderType;
import com.midas.generated.model.UpdateAccountDto;
import java.util.UUID;

public class TestUtils {

  /**
   * Create an account for testing purposes.
   *
   * @return Account
   */
  public static Account createAccount() {
    return Account.builder()
        .id(UUID.randomUUID())
        .firstName("John")
        .lastName("Doe")
        .email("test@test.com")
        .providerId(UUID.randomUUID().toString())
        .providerType(ProviderType.STRIPE)
        .build();
  }

  /**
   * Create an UpdateAccountDto for testing purposes.
   *
   * @return UpdateAccountDto
   */
  public static UpdateAccountDto createUpdateAccountDto() {
    UpdateAccountDto dto = new UpdateAccountDto();
    dto.setFirstName("Jane");
    dto.setLastName("Doe");
    dto.setEmail("updatedtest@test.com");
    return dto;
  }
}
