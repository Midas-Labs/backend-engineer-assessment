package com.midas.app.providers.payment;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateAccount {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
}
