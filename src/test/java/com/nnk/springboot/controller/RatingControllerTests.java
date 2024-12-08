package com.nnk.springboot.controller;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@Transactional
public class RatingControllerTests {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RatingService ratingService;

    private MockMvc mockMvc;

    private Rating testRating;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet Rating pour les tests
        testRating = new Rating();
        testRating.setMoodysRating("1");
        testRating.setSandPRating("1");
        testRating.setFitchRating("1");
        testRating.setOrderNumber(1);
    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowRatingListPage() throws Exception {

        // Enregistrer un Rating pour le test
        ratingService.insert(testRating);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    @WithMockUser
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        // Effectuer une requête POST sur /rating/validate avec des paramètres valides
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "22")
                        .param("sandPRating", "2")
                        .param("fitchRating", "2")
                        .param("orderNumber", "2")
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        // Vérifier que le Rating a été ajouté en base de données
        List<Rating> ratings = ratingService.findAllRating();
        assertThat(ratings, hasItem(hasProperty("moodysRating", is("22"))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un Rating pour le test
        Rating savedRating = ratingService.insert(testRating);

        // Effectuer une requête GET sur /rating/update/{id}
        mockMvc.perform(get("/rating/update/" + savedRating.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attribute("rating", hasProperty("id", is(savedRating.getId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateRating() throws Exception {
        // Enregistrer un Rating pour le test
        Rating savedRating = ratingService.insert(testRating);

        // Effectuer une requête POST sur /rating/update/{id} avec des données mises à jour
        mockMvc.perform(post("/rating/update/" + savedRating.getId())
                        .param("moodysRating", "3")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        // Vérifier que le Rating a été mis à jour
        Rating updatedRating = ratingService.findRating(savedRating.getId());
        assertNotNull(updatedRating);
        assertThat(updatedRating.getMoodysRating(), is("3"));
    }

    @Test
    @WithMockUser
    public void testDeleteRating() throws Exception {
        // Enregistrer un Rating pour le test
        Rating savedRating = ratingService.insert(testRating);
        int id = savedRating.getId();

        // Effectuer une requête GET sur /rating/delete/{id}
        mockMvc.perform(get("/rating/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        // Vérifier que le Rating a été supprimé
        assertFalse(ratingService.existsById(id));
    }

}