package ch.tbz.m450.testing.tools.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Automated tests for the REST interface of the backend (/students).
 * The full Spring context is started, including the H2 database
 * and the 5 students created by the CommandLineRunner in StudentApplication.
 */
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStudents_returnsSeededStudentsAsJson() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[*].name", hasItems("Jonas", "Patrick", "Yves", "Peter", "Ann")))
                .andExpect(jsonPath("$[*].email", hasItem("jonas@tbz.ch")));
    }

    @Test
    void addStudent_thenStudentAppearsInList() throws Exception {
        String newStudent = """
                {"name": "Testperson", "email": "testperson@tbz.ch"}
                """;

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newStudent))
                .andExpect(status().isOk());

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItem("Testperson")))
                .andExpect(jsonPath("$[*].email", hasItem("testperson@tbz.ch")));
    }

    @Test
    void getStudents_allowsRequestsFromAngularFrontend() throws Exception {
        mockMvc.perform(get("/students")
                        .header("Origin", "http://localhost:4200"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }
}