package com.brogs.crm.domain.company;

import com.brogs.crm.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class Company extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;
    private String name;
    private String owner;

    @Builder
    public Company(String name,
                   String owner) {
        //TODO: 예외처리
        this.name = name;
        this.owner = owner;
    }


}
