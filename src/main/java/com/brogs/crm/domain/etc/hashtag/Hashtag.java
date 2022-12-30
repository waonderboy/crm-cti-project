package com.brogs.crm.domain.etc.hashtag;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Hashtag {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;
    @Column(unique = true, nullable = false)
    @Setter
    private String title;

    private Hashtag(String title) {
        this.title = title;
    }

}
