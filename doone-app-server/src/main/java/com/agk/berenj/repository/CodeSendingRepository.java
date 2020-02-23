package com.agk.berenj.repository;

import com.agk.berenj.model.CodeSending;
import com.agk.berenj.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface CodeSendingRepository extends JpaRepository<CodeSending, Long> {

}
