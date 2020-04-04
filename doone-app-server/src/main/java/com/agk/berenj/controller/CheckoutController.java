package com.agk.berenj.controller;

import com.agk.berenj.model.*;
import com.agk.berenj.payload.*;
import com.agk.berenj.repository.ProductRepository;
import com.agk.berenj.repository.UserRepository;
import com.agk.berenj.repository.mongo.OrderIdRepository;
import com.agk.berenj.repository.mongo.OrderRepository;
import com.agk.berenj.exception.ObjectNotFoundException;

import com.agk.berenj.security.CurrentUser;
import com.agk.berenj.security.UserPrincipal;
import io.swagger.annotations.ApiImplicitParam;
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


    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    private VerifyingOrderResponse verifyingorder(@RequestBody OrderRequest orderRequest
            , @CurrentUser UserPrincipal currentUser) {

//        UserInfo userInfo = orderRequest.getUserInfo();
//        String name = userInfo.getName();
        ClientsideOrder clientsideOrder = orderRequest.getClientsideOrder();

        long deliveringAddressId = orderRequest.getDeliveringAddressId();
        PersianDate orderDeliverPersianDate = orderRequest.getOrderDeliverPersianDate();
        long orderDeliverTime = orderRequest.getOrderDeliverTime();

        List<ProductApplied> products = clientsideOrder.getProducts();
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
        Order order = new Order();
        order.setPaymentType(orderRequest.getPaymentType());
        order.setOrderTime(issuedFactorTime);
        order.setProducts(clientsideOrder.getProducts());

        String alphaNumericString = RandomString.getAlphaNumericString(4);
        while (true) {
            if (!orderIdRepository.existsById(alphaNumericString)) {
                orderIdRepository.save(new OrderId().set_id(alphaNumericString));
                break;
            }
            alphaNumericString = RandomString.getAlphaNumericString(4);
        }
        order.set_id(alphaNumericString);
        order.setOrderDeliverPersianDate(orderDeliverPersianDate)
                .setOrderDeliverUnixTime(orderDeliverTime);
        order.setUserId(currentUser.getId());
        order.setOrderStatus(OrderStatus.NOT_PAID_YET);
        Order save = orderRepository.save(order);
        return new VerifyingOrderResponse(payablePrice.get(), save.get_id(), issuedFactorTime, PaymentType.ZARIINPAL);
    }

    @PostMapping("/verify")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
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
