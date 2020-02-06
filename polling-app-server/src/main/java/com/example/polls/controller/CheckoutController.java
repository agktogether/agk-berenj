package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Address;
import com.example.polls.model.Order;
import com.example.polls.model.Product;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    private CheckoutResponse d(@CurrentUser UserPrincipal currentUser) {
        String username = currentUser.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserCheckout userCheckout = new UserCheckout(username, user.getName(), user.getAddresses());
        return new CheckoutResponse().setUser(userCheckout);
    }

    @PostMapping
    private CheckoutResponse verifyorder(@RequestBody OrderRequest orderRequest) {
        UserCheckout userCheckout = orderRequest.getUserCheckout();
        String name = userCheckout.getName();
        Order order = orderRequest.getOrder();
        Address deliveringAddress = orderRequest.getDeliveringAddress();
        PersianDate orderDeliverPersianDate = orderRequest.getOrderDeliverPersianDate();
        long orderDeliverTime = orderRequest.getOrderDeliverTime();

        List<ProductApplied> products = order.getProducts();
        int sum = 0;
        for (ProductApplied product : products) {
            @NotBlank Integer price = product.getPrice();
            @NotBlank @Size(min = 1) Integer count = product.getCount();
            sum += price * count;
        }
        assert sum == order.getTotalPrice();


        return null;

    }


}
