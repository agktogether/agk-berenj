package com.agk.berenj.controller;

import com.agk.berenj.repository.ProductRepository;
import com.agk.berenj.model.Product;
import com.agk.berenj.security.CurrentUser;
import com.agk.berenj.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts(@CurrentUser UserPrincipal currentUser) {
        List<Product> all = productRepository.findAll();
        return all;
    }


}
