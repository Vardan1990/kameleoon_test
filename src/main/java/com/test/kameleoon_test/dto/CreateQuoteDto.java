package com.test.kameleoon_test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuoteDto {

    private Long userId;
    @NotBlank
    private String quoteContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateQuoteDto that)) return false;

        return new EqualsBuilder()
                .append(userId, that.userId)
                .append(quoteContent, that.quoteContent)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(quoteContent)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CreateQuoteDto{" +
                "userId=" + userId +
                ", quoteContent='" + quoteContent + '\'' +
                '}';
    }
}
