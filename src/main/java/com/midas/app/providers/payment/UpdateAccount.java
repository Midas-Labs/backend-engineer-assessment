package com.midas.app.providers.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateAccount {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String providerId;
}
