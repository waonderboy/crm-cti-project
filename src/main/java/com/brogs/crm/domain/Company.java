package com.brogs.crm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Company extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String owner;

    @Builder
    public Company(String name, String owner) {
        //TODO: 예외처리
        this.name = name;
        this.owner = owner;
    }


}
