package com.norman.recipes.security;

import com.norman.recipes.domain.entities.Ingredient;
import com.norman.recipes.domain.entities.Recipe;
import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.IngredientRepository;
import com.norman.recipes.domain.repositories.RecipeRepository;
import com.norman.recipes.domain.repositories.UtilisateurRepository;
import com.norman.recipes.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {
    private final UtilisateurRepository utilisateurRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Override
    public void run(String... args) throws Exception {
        if (utilisateurRepository.findAll().size() == 0){
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("Norman");
            utilisateur.setEmail("mbouendenorman@gmail.com");
            utilisateur.setPassword(passwordEncoder.encode("explorerNate125"));
            utilisateur.setRole("ADMIN");
            utilisateur.setActive(true);
            utilisateurRepository.save(utilisateur);

            Recipe eru = new Recipe();
            eru.setName("Water fufu and eru");
            eru.setDescription("This is a local food from Bamenda Cameroon");
            eru.setUtilisateur(utilisateur);
            recipeRepository.save(eru);

            Recipe ndole = new Recipe();
            ndole.setUtilisateur(utilisateur);
            ndole.setName("Ndole");
            ndole.setDescription("A classic");
            recipeRepository.save(ndole);

            Ingredient ingredient = new Ingredient();
            ingredient.setAmount(4);
            ingredient.setName("Meat");
            ingredient.setRecipe(eru);
            ingredientRepository.save(ingredient);

            ingredient = new Ingredient();
            ingredient.setRecipe(ndole);
            ingredient.setAmount(10);
            ingredient.setName("Peanut");
            ingredientRepository.save(ingredient);
        }

        emailService.send("mbouendenorman@gmail.com", "test test");
        System.out.println("Done");
    }
}
