package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BidListService bidListService;

    private MockMvc mockMvc;

    private BidList testBidList;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet BidList pour les tests

        testBidList = new BidList();
        testBidList.setAccount("Account Test");
        testBidList.setType("Type Test");    ;
        testBidList.setBidQuantity(10d);


    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowBidListListPage() throws Exception {

        // Enregistrer un BidList pour le test
        bidListService.insert(testBidList);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));
    }

    @Test
    @WithMockUser
    public void testAddBidListForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        // Effectuer une requête POST sur /bidList/validate avec des paramètres valides
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account Test2")
                        .param("type", "type Test2")
                        .param("bidQuantity", "20.0")
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        // Vérifier que le BidList a été ajouté en base de données
        List<BidList> bidLists = bidListService.findAll();
        assertThat(bidLists, hasItem(hasProperty("account", is("Account Test2"))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un BidList pour le test
        BidList savedBidList = bidListService.insert(testBidList);

        // Effectuer une requête GET sur /bidList/update/{id}
        mockMvc.perform(get("/bidList/update/" + savedBidList.getBidListId()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().attribute("bidList", hasProperty("bidListId", is(savedBidList.getBidListId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateBidList() throws Exception {
        // Étape 1 : Créer une BidList initiale
        BidList savedBidList = bidListService.insert(testBidList);

        // Étape 2 : Simuler une mise à jour via MockMvc
        mockMvc.perform(post("/bidList/update/{id}", savedBidList.getBidListId())
                        .param("account", "Account Test Updated") // Paramètres mis à jour
                        .param("type", "Type Test Updated")
                        .param("bidQuantity", "30.0")
                        .with(csrf())) // Jeton CSRF si nécessaire
                .andExpect(status().is3xxRedirection()) // Vérifiez la redirection
                .andExpect(redirectedUrl("/bidList/list"));

        // Étape 3 : Vérifier que la BidList a bien été mise à jour
        BidList updatedBidList = bidListService.findBidList(savedBidList.getBidListId());
        assertNotNull(updatedBidList); // Vérifie que l'objet n'est pas nul
        assertEquals("Account Test Updated", updatedBidList.getAccount()); // Vérifie le champ "account"
        assertEquals("Type Test Updated", updatedBidList.getType()); // Vérifie le champ "type"
        assertEquals(30.0, updatedBidList.getBidQuantity()); // Vérifie le champ "bidQuantity"
    }



    @Test
    @WithMockUser
    public void testDeleteBidList() throws Exception {
        // Enregistrer un BidList pour le test
        BidList savedBidList = bidListService.insert(testBidList);
        int id = savedBidList.getBidListId();

        // Effectuer une requête GET sur /bidList/delete/{id}
        mockMvc.perform(get("/bidList/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        // Vérifier que le BidList a été supprimé
        assertFalse(bidListService.existsById(id));
    }

}