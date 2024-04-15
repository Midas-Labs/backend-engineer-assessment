import static org.junit.jupiter.api.Assertions.assertEquals;

import com.midas.app.enums.PaymentProviderType;
import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.StripeConfiguration;
import com.midas.app.providers.external.stripe.StripePaymentProvider;
import com.midas.app.providers.payment.CreateAccount;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class StripePaymentProviderTest {
  @Mock private StripeConfiguration configuration;
  private StripePaymentProvider stripePaymentProvider;

  @BeforeEach
  public void setup() {
    configuration = new StripeConfiguration();
    configuration.setApiKey(
        "sk_test_51P1BOQSCwIj1couMVD4Dt414GmjzUBYAbyzsK33aZK6eogwCalQECJy2vOMimpfQ3iuxhbKo9yCynmZlYpDFNeiI00qOgtzitV");
    stripePaymentProvider = new StripePaymentProvider(configuration);
  }

  @Test
  public void testCreateAccount() throws StripeException {
    String apiKey =
        "sk_test_51P1BOQSCwIj1couMVD4Dt414GmjzUBYAbyzsK33aZK6eogwCalQECJy2vOMimpfQ3iuxhbKo9yCynmZlYpDFNeiI00qOgtzitV";
    String firstName = "John";
    String lastName = "Doe";
    String email = "john.doe@example.com";
    String userId = "218c79b3-bb34-4caf-86de-c4cfa327ae6c";
    Stripe.apiKey = apiKey;
    CreateAccount details = new CreateAccount(userId, firstName, lastName, email);
    Account account = stripePaymentProvider.createAccount(details);

    // Assert
    assertEquals(firstName, account.getFirstName());
    assertEquals(lastName, account.getLastName());
    assertEquals(email, account.getEmail());
    Assertions.assertNotNull(account.getProviderId());
    assertEquals(PaymentProviderType.Stripe, account.getPaymentProviderType());
  }
}
