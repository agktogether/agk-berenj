package com.agk.berenj.repository.mongo;

import com.agk.berenj.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findBy_idAndUserId(String id, long userid);
}
