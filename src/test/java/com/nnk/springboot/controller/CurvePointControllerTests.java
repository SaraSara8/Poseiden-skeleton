package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CurvePointService curvePointService;

    private MockMvc mockMvc;

    private CurvePoint testCurvePoint;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet CurvePoint pour les tests
        testCurvePoint = new CurvePoint();

        testCurvePoint.setCurveId(10);
        testCurvePoint.setTerm(10d);
        testCurvePoint.setValue(10d);

    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowCurvePointListPage() throws Exception {

        // Enregistrer un CurvePoint pour le test
        curvePointService.insert(testCurvePoint);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        // Effectuer une requête POST sur /curvePoint/validate avec des paramètres valides
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "20")
                        .param("term", "20.0")
                        .param("value", "20.0")
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        // Vérifier que le CurvePoint a été ajouté en base de données
        List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
        assertThat(curvePoints, hasItem(hasProperty("value", is(20.0))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un CurvePoint pour le test
        CurvePoint savedCurvePoint = curvePointService.insert(testCurvePoint);

        // Effectuer une requête GET sur /curvePoint/update/{id}
        mockMvc.perform(get("/curvePoint/update/" + savedCurvePoint.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", hasProperty("id", is(savedCurvePoint.getId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateCurvePoint() throws Exception {
        // Enregistrer un CurvePoint pour le test
        CurvePoint savedCurvePoint = curvePointService.insert(testCurvePoint);

        // Effectuer une requête POST sur /curvePoint/update/{id} avec des données mises à jour
        mockMvc.perform(post("/curvePoint/update/" + savedCurvePoint.getId())
                        .param("CurveId", "10")
                        .param("term", "10.0")
                        .param("value", "11.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        // Vérifier que le CurvePoint a été mis à jour
        CurvePoint updatedCurvePoint = curvePointService.findCurvePoint(savedCurvePoint.getId());
        assertNotNull(updatedCurvePoint);
        assertThat(updatedCurvePoint.getValue(), is(11.0));
    }

    @Test
    @WithMockUser
    public void testDeleteCurvePoint() throws Exception {
        // Enregistrer un CurvePoint pour le test
        CurvePoint savedCurvePoint = curvePointService.insert(testCurvePoint);
        int id = savedCurvePoint.getId();

        // Effectuer une requête GET sur /curvePoint/delete/{id}
        mockMvc.perform(get("/curvePoint/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        // Vérifier que le CurvePoint a été supprimé
        assertFalse(curvePointService.existsById(id));
    }

}