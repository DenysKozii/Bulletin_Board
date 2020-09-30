package com.denyskozii.bulletinboard.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "boards")
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Must not be blank")
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;



    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private User author;
}
