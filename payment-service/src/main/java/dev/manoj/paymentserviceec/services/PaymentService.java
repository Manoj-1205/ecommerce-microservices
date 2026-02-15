package dev.manoj.paymentserviceec.services;

import com.stripe.exception.StripeException;
import dev.manoj.paymentserviceec.paymentGateways.stripe.StripePaymentGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private StripePaymentGateway stripePaymentGateway;
    public String createPaymentLink(Long orderId) throws StripeException {

        return stripePaymentGateway.generatePaymentLink(10000L);
    }
}
