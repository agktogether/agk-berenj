package com.example.polls.controller;

import com.example.polls.exception.AppException;
import com.example.polls.exception.ObjectNotFoundException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.ProductRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.mongo.OrderIdRepository;
import com.example.polls.repository.mongo.OrderRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderIdRepository orderIdRepository;

    @GetMapping
    private CheckoutResponse d(@CurrentUser UserPrincipal currentUser) {
        String username = currentUser.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserCheckout userCheckout = new UserCheckout(username, user.getName(), user.getAddresses());
        return new CheckoutResponse().setUser(userCheckout);
    }

    @PostMapping
    private VerifyingOrderResponse verifyingorder(@RequestBody OrderRequest orderRequest, @CurrentUser UserPrincipal currentUser) {

        UserCheckout userCheckout = orderRequest.getUserCheckout();
        String name = userCheckout.getName();
        Order order = orderRequest.getOrder();

        Address deliveringAddress = orderRequest.getDeliveringAddress();
        PersianDate orderDeliverPersianDate = orderRequest.getOrderDeliverPersianDate();
        long orderDeliverTime = orderRequest.getOrderDeliverTime();

        List<ProductApplied> products = order.getProducts();
        AtomicInteger payablePrice = new AtomicInteger(0);
        for (ProductApplied product : products) {
            Long id = product.getId();
            Optional<Product> byId = productRepository.findById(id);
            byId.ifPresent(product1 -> {
                @NotBlank Integer price = product1.getPrice();
                @NotBlank @Size(min = 1) Integer count = product.getCount();
                payablePrice.addAndGet(price * count);
            });
        }
        long issuedFactorTime = System.currentTimeMillis();
        order.setOrderTime(issuedFactorTime);
        String alphaNumericString = RandomString.getAlphaNumericString(4);
        while (true) {
            if (!orderIdRepository.existsById(alphaNumericString)) {
                orderIdRepository.save(new OrderId().set_id(alphaNumericString));
                break;
            }
            alphaNumericString = RandomString.getAlphaNumericString(4);
        }
        order.set_id(alphaNumericString);
        order.setUserId(currentUser.getId());
        order.setOrderStatus(OrderStatus.NOT_PAID_YET);
        Order save = orderRepository.save(order);
        return new VerifyingOrderResponse(payablePrice.get(), save.get_id(), issuedFactorTime, PaymentType.ZARIINPAL);
    }

    @PostMapping("/verify")
    private Order verifyforpayment(@RequestBody OrderId orderid, @CurrentUser UserPrincipal currentUser) {

        Order order = orderRepository.findBy_idAndUserId(orderid.get_id(), currentUser.getId())
                .orElseThrow(() -> new ObjectNotFoundException("order not found for this user"));
//                .orElseThrow(() -> new AppException("order not found for this user"));



        List<ProductApplied> products = order.getProducts();

        AtomicInteger payablePrice = new AtomicInteger(0);
        for (ProductApplied product : products) {
            Long id = product.getId();
            Optional<Product> byId = productRepository.findById(id);
            byId.ifPresent(product1 -> {
                @NotBlank Integer price = product1.getPrice();
                @NotBlank @Size(min = 1) Integer count = product.getCount();
                payablePrice.addAndGet(price * count);
            });
        }
        order.setOrderStatus(OrderStatus.SENT_FOR_PAYING);
        Order save = orderRepository.save(order);
        return save;
    }


}
