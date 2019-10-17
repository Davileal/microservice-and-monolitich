package br.com.monolithic.ecommerce.services;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.domain.Payment;
import br.com.monolithic.ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void doPayment(Order order) {
        Payment payment = new Payment(order);
        paymentRepository.save(payment);
    }

}
