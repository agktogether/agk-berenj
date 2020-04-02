package com.agk.berenj.repository;

import com.agk.berenj.model.Address;
import com.agk.berenj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByLatAndLng(boolean lat, boolean lng);
}
