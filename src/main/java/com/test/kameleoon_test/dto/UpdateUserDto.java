package com.test.kameleoon_test.dto;

import jakarta.validation.constraints.Email;
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
public class UpdateUserDto {

    private String userName;
    @Email
    private String email;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateUserDto that)) return false;

        return new EqualsBuilder()
                .append(userName, that.userName)
                .append(email, that.email)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(email)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
