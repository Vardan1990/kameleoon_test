package com.test.kameleoon_test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



@Getter
@Setter
@Entity
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "vote_count")
    private Long voteCount;
    @ManyToOne
    @JoinColumn(name = "quote_id")
    @JsonBackReference
    private Quote quote;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(voteCount, vote.voteCount)
                .append(quote, vote.quote)
                .append(user, vote.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(voteCount)
                .append(quote)
                .append(user)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteCount=" + voteCount +
                ", quoteId=" + quote +
                ", userId=" + user +
                "} " + super.toString();
    }
}
