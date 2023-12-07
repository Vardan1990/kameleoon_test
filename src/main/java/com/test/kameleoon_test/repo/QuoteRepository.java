package com.test.kameleoon_test.repo;


import com.test.kameleoon_test.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByUserId(Long id);

    @Query(nativeQuery = true, value = "with random_ids as (select *\n" +
            "                    from (select distinct 1 +\n" +
            "                                          cast(trunc(random() * (select last_value from quote_id_seq)) as bigint) as id\n" +
            "                          from generate_series(1, 10000) g) r)\n" +
            "select q.*\n" +
            "from quote q\n" +
            "where q.id in (select rid.id from random_ids rid)\n" +
            "limit 1")
    Quote findRandomQuote();

    @Query(nativeQuery = true, value = "SELECT q.* FROM Quote q ORDER BY q.vote_amount DESC")
    List<Quote> findTop10Quotes(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT q.* FROM Quote q ORDER BY q.vote_amount  ASC ")
    List<Quote> findWorst10Quotes(Pageable pageable);
}
