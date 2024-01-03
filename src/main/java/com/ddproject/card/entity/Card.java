package com.ddproject.card.entity;

import jakarta.persistence.*;
import com.ddproject.column.entity.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Column column;

    private String name;
    private String description;
    private String color;
    private Integer sequence;
    private Boolean isDeleted;

}
