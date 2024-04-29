package com.midas.app.providers.external.stripe;

import com.midas.app.exceptions.ApiException;
import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.mapper.StripeCustomerMapper;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.providers.payment.UpdateAccount;
import com.stripe.StripeClient;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
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
    logger.info("Creating Stripe account  for user with email: {}", details.getEmail());

    StripeClient client = new StripeClient(configuration.getApiKey());
    CustomerCreateParams params = StripeCustomerMapper.map(details);
    try {
      Customer customer = client.customers().create(params);
      return StripeCustomerMapper.mapToAccount(customer);
    } catch (Exception e) {
      String errorMessage =
          "Error creating Stripe account for user with email: "
              + details.getEmail()
              + " error: "
              + e.getMessage();
      ;
      logger.error(errorMessage, e);
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
  }

  /**
   * updateAccount updates an existing account in the payment provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  @Override
  public Account updateAccount(UpdateAccount details) {
    logger.info("Updating Stripe account for user with email: {}", details.getEmail());

    StripeClient client = new StripeClient(configuration.getApiKey());
    CustomerUpdateParams params = StripeCustomerMapper.map(details);
    try {
      System.out.println("details.getUserId() = " + details.getUserId());
      Customer resource = client.customers().retrieve(details.getUserId());
      if (resource == null) {
        logger.error("Stripe Account not found for user with id: {}", details.getUserId());
        throw new ApiException(HttpStatus.NOT_FOUND, "Strip Account not found");
      }
      Customer customer = resource.update(params);
      return StripeCustomerMapper.mapToAccount(customer);
    } catch (Exception e) {
      // TODO: map all StripeException to ApiExceptions
      String errorMessage =
          "Error updating Stripe account for user with id: "
              + details.getUserId()
              + " error: "
              + e.getMessage()
              + " cause: "
              + e.getCause();
      logger.error(errorMessage, e);
      throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, errorMessage);
    }
  }
}
