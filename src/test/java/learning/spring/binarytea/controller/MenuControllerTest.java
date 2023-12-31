package learning.spring.binarytea.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MenuControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterEach
    void tearDown() {
        mockMvc = null;
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/menu"))
                .andExpect(handler().methodName("getAll"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$..name").value(Matchers.hasItem("Java咖啡")));
    }

    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get("/menu/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(MenuController.class))
                .andExpect(handler().methodName("getById"))
                .andExpect(jsonPath("$.name").value("Java咖啡"))
                .andExpect(jsonPath("$.size").value("MEDIUM"));
    }

    @Test
    void testGetByIdWithWrongId() throws Exception {
        mockMvc.perform(get("/menu/100"))
                .andExpect(content().string("null"));
    }

    @Test
    void testGetByName() throws Exception {
        mockMvc.perform(get("/menu").param("name", "Java咖啡"))
                .andExpect(handler().handlerType(MenuController.class))
                .andExpect(handler().methodName("getByName"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetByNameWithWrongName() throws Exception {
        mockMvc.perform(get("/menu").param("name", "Java"))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testCreateBatchWithEmptyFile() throws Exception {
        mockMvc.perform(multipart("/menu")
                            .file("file", null))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[]"));

    }
}
