package com.brogs.crm.domain.manual;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class Manual extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "manual_id")
    private Long id;
    private String title;
    private String content;

    @JoinTable(
            name = "manual_hashtag",
            joinColumns = @JoinColumn(name = "manual_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    @Builder
    public Manual(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
