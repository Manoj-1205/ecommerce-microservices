package dev.manoj.paymentserviceec.paymentGateways;

import com.stripe.exception.StripeException;

public interface PaymentGateway {
     String generatePaymentLink(Long amount) throws StripeException;
}
