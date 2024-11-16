package it.infotechconsults.bric48.backend.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.infotechconsults.bric48.backend.rest.dto.UserDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(username = "admin@system.it", password = "password")
    void testCreate() throws Exception {
        UserDTO request = new UserDTO();
        request.setName("Administrator 1");
        request.setSurname("Administrator 1");
        request.setEmail("admin1@system.it");
        request.setPassword("password");
        request.setRoleId("ADMINISTRATOR");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("admin@system.it"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin@system.it", password = "password")
    void testCreateFail() throws Exception {
        UserDTO request = new UserDTO();
        request.setName("Administrator");
        request.setSurname("Administrator");
        request.setEmail("admin@system.it");
        request.setPassword("password");
        request.setRoleId("NOT_EXISTS");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testDelete() {

    }

    @Test
    void testGetById() {

    }

    @Test
    void testSearch() {

    }

    @Test
    void testUpdate() {

    }
}
