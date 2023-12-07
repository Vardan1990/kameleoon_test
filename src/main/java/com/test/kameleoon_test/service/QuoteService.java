package com.test.kameleoon_test.service;

import com.test.kameleoon_test.dto.CreateQuoteDto;
import com.test.kameleoon_test.dto.UpdateQuoteDto;
import com.test.kameleoon_test.entity.Quote;
import com.test.kameleoon_test.entity.User;
import com.test.kameleoon_test.exceptions.UserException;
import com.test.kameleoon_test.repo.QuoteRepository;
import com.test.kameleoon_test.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Quote createQuote(CreateQuoteDto createQuoteDto) {
        try {
            Optional<User> userOptional = userRepository.findById(createQuoteDto.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return quoteRepository.save(new Quote(createQuoteDto.getQuoteContent(), null, user, null));
            } else {
                throw new UserException("user by this id not found");
            }
        } catch (Exception e) {
            log.error("can not create quote {}", e.getMessage(), e);
            return null;
        }
    }

    @Transactional
    public void deleteQuote(Long id) {
        quoteRepository.findById(id).ifPresent(quoteRepository::delete);
    }

    public Optional<Quote> getQuote(Long quoteId) {
        try {
            return quoteRepository.findById(quoteId);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<Quote> getQuoteList(Long userId) {
        List<Quote> quoteList = quoteRepository.findAllByUserId(userId);
        if (quoteList.isEmpty()) {
            throw new EntityNotFoundException("quotes by this user not found");
        }
        return quoteList;
    }

    @Transactional
    public synchronized Optional<Quote> updateQuote(UpdateQuoteDto updateQuoteDto) {
        Optional<Quote> quoteOptional = quoteRepository.findById(updateQuoteDto.getQuoteId());
        boolean updated = false;
        if (quoteOptional.isEmpty()) {
            return Optional.empty();
        }
        Quote quote = quoteOptional.get();
        if (!StringUtils.isBlank(updateQuoteDto.getQuoteContent())) {
            log.info("quote content {}", updateQuoteDto.getQuoteContent());
            quote.setQuoteContent(updateQuoteDto.getQuoteContent());
            updated = true;
        }
        if (updateQuoteDto.getVoteAmount() != null) {
            log.info("vote amount {} requested {}", quote.getVoteAmount(), updateQuoteDto.getVoteAmount());
            quote.setVoteAmount(quote.getVoteAmount() != null ? quote.getVoteAmount() + updateQuoteDto.getVoteAmount() : updateQuoteDto.getVoteAmount());
            updated = true;
        }
        if (!updated) {
            return Optional.of(quote);
        }
        return Optional.of(quoteRepository.save(quote));
    }

    public Quote getRandomQuote() {
        log.info("random quote id");
        return quoteRepository.findRandomQuote();
    }

    public List<Quote> getTopTenQuotes() {
        Pageable pageable = PageRequest.of(0, 10);
        return quoteRepository.findTop10Quotes(pageable);
    }

    public List<Quote> getWorstTenQuotes() {
        Pageable pageable = PageRequest.of(0, 10);
        return quoteRepository.findWorst10Quotes(pageable);
    }
}
