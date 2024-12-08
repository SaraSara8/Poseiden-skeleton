package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private User testUser;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet User pour les tests

        testUser = new User();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");

        testUser.setUsername("Toto");
        testUser.setFullname("TOTO toto");   ;
        testUser.setPassword(pw);
        testUser.setRole("USER");


    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowUserListPage() throws Exception {

        // Enregistrer un User pour le test
        userService.insert(testUser);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser
    public void testAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");
        // Effectuer une requête POST sur /user/validate avec des paramètres valides
        mockMvc.perform(post("/user/validate")
                        .param("username", "Toto")
                        .param("role", "USER")
                        .param("fullname", "TOTO Toto")
                        .param("password", pw)
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Vérifier que le User a été ajouté en base de données
        List<User> users = userService.findAllUsers();
        assertThat(users, hasItem(hasProperty("username", is("Toto"))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un User pour le test
        User savedUser = userService.insert(testUser);

        // Effectuer une requête GET sur /user/update/{id}
        mockMvc.perform(get("/user/update/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("id", is(savedUser.getId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        // Enregistrer un User pour le test
        User savedUser = userService.insert(testUser);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("78910");

        // Effectuer une requête POST sur /user/update/{id} avec des données mises à jour
        mockMvc.perform(post("/user/update/" + savedUser.getId())
                        .param("username", "Toto")
                        .param("role", "USER")
                        .param("fullname", "TOTO Titi")
                        .param("password",pw)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Vérifier que le User a été mis à jour
        User updatedUser = userService.findUser(savedUser.getId());
        assertNotNull(updatedUser);
        assertThat(updatedUser.getFullname(), is("TOTO Titi"));
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {
        // Enregistrer un User pour le test
        User savedUser = userService.insert(testUser);
        int id = savedUser.getId();

        // Effectuer une requête GET sur /user/delete/{id}
        mockMvc.perform(get("/user/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Vérifier que le User a été supprimé
        assertFalse(userService.existsById(id));
    }

}