package com.denyskozii.bulletinboard.dto;

import com.denyskozii.bulletinboard.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BulletinDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private UserDto author;
}
