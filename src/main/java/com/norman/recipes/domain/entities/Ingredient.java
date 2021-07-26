package com.norman.recipes.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient extends BaseEntity{
    private String name;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;
}
