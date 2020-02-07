package com.example.polls.repository.mongo;

import com.example.polls.model.Order;
import com.example.polls.model.OrderId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderIdRepository extends MongoRepository<OrderId, String> {

}
