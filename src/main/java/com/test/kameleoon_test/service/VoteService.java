package com.test.kameleoon_test.service;

import com.test.kameleoon_test.dto.UpdateQuoteDto;
import com.test.kameleoon_test.entity.Quote;
import com.test.kameleoon_test.entity.User;
import com.test.kameleoon_test.entity.Vote;
import com.test.kameleoon_test.repo.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final QuoteService quoteService;
    private final UserService userService;


    @Transactional
    public void voteQuotePlus(Long userId, Long quoteId, Long voteAmount) {
        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Quote> quoteOptional = quoteService.getQuote(quoteId);
        if (optionalUser.isPresent() && quoteOptional.isPresent()) {
            log.info("update quote vote plus");
            quoteService.updateQuote(updateQuoteVote(quoteId, voteAmount));
            createVote(voteAmount, optionalUser.get(), quoteOptional.get());
        }
    }

    @Transactional
    public void voteQuoteMinus(Long userId, Long quoteId, Long voteAmount) {
        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Quote> quoteOptional = quoteService.getQuote(quoteId);
        if (optionalUser.isPresent() && quoteOptional.isPresent()) {
            log.info("update quote vote minus");
            quoteService.updateQuote(updateQuoteVote(quoteId, (-voteAmount)));
            createVote((-voteAmount), optionalUser.get(), quoteOptional.get());
        }
    }

    private UpdateQuoteDto updateQuoteVote(Long quoteId, Long voteAmount) {
        UpdateQuoteDto updateQuoteDto = new UpdateQuoteDto();
        updateQuoteDto.setQuoteId(quoteId);
        updateQuoteDto.setVoteAmount(voteAmount);
        return updateQuoteDto;
    }

    private void createVote(Long voteAmount, User user, Quote quote) {
        log.info("create vote");
        voteRepository.save(new Vote(voteAmount, quote, user));
    }

    public List<Vote> getVoteChangeGraphByQuoteIdAndTimePeriod(Long quoteId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay().withSecond(0);
        LocalDateTime endOfDay = endDate.atStartOfDay().plusDays(1).withSecond(0);
        List<Vote> voteList = voteRepository.findByQuoteIdAndTimePeriod(quoteId, startOfDay, endOfDay);
        if (voteList.isEmpty()) {
            throw new EntityNotFoundException("vote list by this quote id not found");
        }
        return voteList;
    }
}
