package com.agk.berenj.repository.mongo;

import com.agk.berenj.model.OrderId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderIdRepository extends MongoRepository<OrderId, String> {

}
