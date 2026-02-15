package dev.manoj.paymentserviceec.controllers;

import com.stripe.exception.StripeException;
import dev.manoj.paymentserviceec.dtos.CreatePaymentLinkRequestDTO;
import dev.manoj.paymentserviceec.services.PaymentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@AllArgsConstructor

public class PaymentController {

    private PaymentService paymentService;

    @PostMapping()
    public String createPaymentLink(@RequestBody CreatePaymentLinkRequestDTO requestDTO) throws StripeException {
        return paymentService.createPaymentLink(requestDTO.getOrderId());
    }
}
