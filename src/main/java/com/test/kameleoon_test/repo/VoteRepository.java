package com.test.kameleoon_test.repo;


import com.test.kameleoon_test.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(nativeQuery = true, value = "select v.* from vote v WHERE v.created BETWEEN :from AND :to and v.quote_id=:quoteId")
    List<Vote> findByQuoteIdAndTimePeriod(@Param("quoteId") Long quoteId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
