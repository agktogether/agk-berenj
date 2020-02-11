package com.agk.berenj.repository;

import com.agk.berenj.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long pollId);


    List<Product> findByIdIn(List<Long> productIds);

    List<Product> findByIdIn(List<Long> productIds, Sort sort);
}
