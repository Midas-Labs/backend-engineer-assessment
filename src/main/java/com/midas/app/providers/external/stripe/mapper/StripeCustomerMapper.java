package com.midas.app.providers.external.stripe.mapper;

import com.midas.app.models.Account;
import com.midas.app.models.enums.ProviderType;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.UpdateAccount;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;

public class StripeCustomerMapper {

  /**
   * map maps a CreateAccount to a customer create params.
   *
   * @param details is the details of the account to be created.
   * @return CustomerCreateParams
   */
  public static CustomerCreateParams map(CreateAccount details) {
    return CustomerCreateParams.builder()
        .setEmail(details.getEmail())
        .setName(details.getFirstName() + " " + details.getLastName())
        .build();
  }

  /**
   * map maps a UpdateAccount to a customer update params.
   *
   * @param details is the details of the account to be updated.
   * @return CustomerUpdateParams
   */
  public static CustomerUpdateParams map(UpdateAccount details) {
    CustomerUpdateParams.Builder builder = CustomerUpdateParams.builder();

    if (details.getFirstName() != null) {
      builder.setName(details.getFirstName() + " " + details.getLastName());
    }
    if (details.getEmail() != null) {
      builder.setEmail(details.getEmail());
    }
    return builder.build();
  }

  /**
   * mapToAccount maps a customer to an account.
   *
   * @param customer is the customer to be mapped
   * @return Account
   */
  public static Account mapToAccount(Customer customer) {
    return Account.builder()
        .firstName(customer.getName())
        .lastName(customer.getName())
        .email(customer.getEmail())
        .providerType(ProviderType.STRIPE)
        .providerId(customer.getId())
        .build();
  }
}
