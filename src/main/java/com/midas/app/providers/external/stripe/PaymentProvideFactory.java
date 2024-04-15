package com.midas.app.providers.external.stripe;

import com.midas.app.enums.PaymentProviderType;
import com.midas.app.providers.payment.PaymentProvider;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentProvideFactory {

  /**
   * This is a factory class that creates a payment provider based on the payment provider type. The
   * factory class is responsible for creating the payment provider based on the payment provider
   * type. The factory class uses a map to store the payment provider instances. The factory class
   * initializes
   */
  @Autowired List<PaymentProvider> paymentProviderList;

  private static final Map<String, PaymentProvider> serviceCache = new HashMap<>();

  /**
   * This method initializes the payment provider cache. The method is annotated with @PostConstruct
   * to ensure that the cache is initialized when the bean is created. The method iterates over the
   * list of payment providers and adds them to the cache using the provider name as the key.
   */
  @PostConstruct
  public void initMyServiceCache() {
    for (PaymentProvider service : paymentProviderList) {
      serviceCache.put(service.providerName(), service);
    }
  }

  /**
   * This method returns the payment provider based on the payment provider type. The method takes
   * the payment provider type as an argument and returns the payment provider instance from the
   * cache. If the payment provider is not found in the cache, the method throws a runtime
   * exception.
   */
  public static PaymentProvider getPaymentProvider(PaymentProviderType paymentProviderType) {
    PaymentProvider paymentProvider = serviceCache.get(paymentProviderType.name().toLowerCase());
    if (Objects.isNull(paymentProvider))
      throw new RuntimeException("Unknown payment provider type: " + paymentProviderType);
    return paymentProvider;
  }
}
