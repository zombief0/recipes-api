package com.norman.recipes.domain.entities;

import com.norman.recipes.service.dto.RecipeSaveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Recipe extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imagePath;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    private Utilisateur utilisateur;

    public Recipe(RecipeSaveDto recipeSaveDto){
        this.name = recipeSaveDto.getName();
        this.description = recipeSaveDto.getDescription();
        this.imagePath = recipeSaveDto.getImagePath();
    }
}
