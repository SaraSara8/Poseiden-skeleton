package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TradeService tradeService;

    private MockMvc mockMvc;

    private Trade testTrade;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet Trade pour les tests

        testTrade = new Trade();
        testTrade.setAccount("Trade Account");
        testTrade.setType("Type");    ;



    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowTradeListPage() throws Exception {

        // Enregistrer un Trade pour le test
        tradeService.insert(testTrade);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        // Effectuer une requête POST sur /trade/validate avec des paramètres valides
        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account Test2")
                        .param("type", "type Test2")
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        // Vérifier que le Trade a été ajouté en base de données
        List<Trade> trades = tradeService.findAllTrade();
        assertThat(trades, hasItem(hasProperty("account", is("Account Test2"))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un Trade pour le test
        Trade savedTrade = tradeService.insert(testTrade);

        // Effectuer une requête GET sur /trade/update/{id}
        mockMvc.perform(get("/trade/update/" + savedTrade.getTradeId()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attribute("trade", hasProperty("tradeId", is(savedTrade.getTradeId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateTrade() throws Exception {
        // Enregistrer un Trade pour le test
        Trade savedTrade = tradeService.insert(testTrade);

        // Effectuer une requête POST sur /trade/update/{id} avec des données mises à jour
        mockMvc.perform(post("/trade/update/" + savedTrade.getTradeId())
                        .param("account", "Account Test3")
                        .param("type", "type Test")
                        .param("bidQuantity", "2O.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        // Vérifier que le Trade a été mis à jour
        Trade updatedTrade = tradeService.findTrade(savedTrade.getTradeId());
        assertNotNull(updatedTrade);
        assertThat(updatedTrade.getAccount(), is("Account Test3"));
    }

    @Test
    @WithMockUser
    public void testDeleteTrade() throws Exception {
        // Enregistrer un Trade pour le test
        Trade savedTrade = tradeService.insert(testTrade);
        int id = savedTrade.getTradeId();

        // Effectuer une requête GET sur /trade/delete/{id}
        mockMvc.perform(get("/trade/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        // Vérifier que le Trade a été supprimé
        assertFalse(tradeService.existsById(id));
    }

}