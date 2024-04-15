package com.midas.app.providers.external.stripe;

import com.midas.app.enums.PaymentProviderType;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.providers.payment.UpdateAccount;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class StripePaymentProvider implements PaymentProvider {
  private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);
  private final StripeConfiguration configuration;

  /** providerName is the name of the payment provider */
  @Override
  public String providerName() {
    return "stripe";
  }

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(CreateAccount details) {
    try {
      Stripe.apiKey = configuration.getApiKey();
      CustomerCreateParams params =
          CustomerCreateParams.builder()
              .setName(details.getFirstName() + " " + details.getLastName())
              .setEmail(details.getEmail())
              .build();
      Customer customer = Customer.create(params);
      log.info("Stripe Payment Customer Created Successfully with Id {}", customer.getId());
      return getAccount(details, customer);
    } catch (StripeException e) {
      log.error("Error Initiating Stripe Customer {}", e.getStripeError());
      throw new RuntimeException(e);
    }
  }

  /**
   * updateAccount updates an account in the payment provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  @Override
  public Account updateAccount(UpdateAccount details) {
    try {
      Stripe.apiKey = configuration.getApiKey();
      Customer resource = Customer.retrieve(details.getProviderId());
      CustomerUpdateParams params =
          CustomerUpdateParams.builder()
              .setName(details.getFirstName() + " " + details.getLastName())
              .setEmail(details.getEmail())
              .build();
      Customer customer = resource.update(params);
      log.info("Stripe Payment Customer Updated Successfully with Id {}", customer.getId());
      return getAccount(details, customer);
    } catch (StripeException e) {
      log.error("Error Initiating Update Stripe Customer {}", e.getStripeError());
      throw new RuntimeException(e);
    }
  }

  /**
   * getAccount creates an account object from the details and the customer object.
   *
   * @param details is the details of the account to be created.
   * @param customer is the customer object created by the payment provider.
   * @return Account
   */
  private Account getAccount(CreateAccount details, Customer customer) {
    return Account.builder()
        .id(UUID.fromString(details.getUserId()))
        .firstName(details.getFirstName())
        .lastName(details.getLastName())
        .email(details.getEmail())
        .paymentProviderType(PaymentProviderType.Stripe)
        .providerId(customer.getId())
        .build();
  }

  /**
   * getAccount creates an account object from the details and the customer object.
   *
   * @param details is the details of the account to be created.
   * @param customer is the customer object created by the payment provider.
   * @return Account
   */
  private Account getAccount(UpdateAccount details, Customer customer) {
    return Account.builder()
        .id(UUID.fromString(details.getUserId()))
        .firstName(details.getFirstName())
        .lastName(details.getLastName())
        .email(details.getEmail())
        .paymentProviderType(PaymentProviderType.Stripe)
        .providerId(customer.getId())
        .build();
  }
}
