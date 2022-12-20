package com.brogs.crm.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Hashtag {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;

    private Hashtag(String title) {
        this.title = title;
    }

    public static Hashtag of(String title){
        return new Hashtag(title);
    }
}
