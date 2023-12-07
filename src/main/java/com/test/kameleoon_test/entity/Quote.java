package com.test.kameleoon_test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "quote")
@NoArgsConstructor
@AllArgsConstructor
public class Quote extends BaseEntity {

    @Column(name = "quote_content")
    private String quoteContent;
    @Column(name = "vote_amount")
    private Long voteAmount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @JsonManagedReference
    @OneToMany(mappedBy = "quote",cascade = CascadeType.ALL)
    private List<Vote> voteList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(quoteContent, quote.quoteContent)
                .append(voteAmount, quote.voteAmount)
                .append(user, quote.user)
                .append(voteList, quote.voteList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(quoteContent)
                .append(voteAmount)
                .append(user)
                .append(voteList)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteContent='" + quoteContent + '\'' +
                ", voteAmount=" + voteAmount +
                ", user=" + user +
                ", voteList=" + voteList +
                "} " + super.toString();
    }
}
