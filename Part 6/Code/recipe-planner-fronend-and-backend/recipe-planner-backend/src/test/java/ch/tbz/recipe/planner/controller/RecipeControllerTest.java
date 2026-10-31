package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService service;

    @MockBean
    private RecipeEntityMapper mapper;

    @MockBean
    private RecipeRepository repository;

    private Recipe sampleRecipe(UUID id) {
        Ingredient flour = new Ingredient(UUID.randomUUID(), "Flour", "sifted", Unit.GRAMM, 500);
        return new Recipe(id, "Bread", "Tasty bread", "http://img/bread.png", List.of(flour));
    }

    @Test
    void getRecipes_returnsAllRecipes() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.getRecipes()).thenReturn(List.of(sampleRecipe(id)));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].name").value("Bread"))
                .andExpect(jsonPath("$[0].ingredients[0].unit").value("GRAMM"));
    }

    @Test
    void getRecipes_returnsEmptyListWhenNoRecipes() throws Exception {
        when(service.getRecipes()).thenReturn(List.of());

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getRecipe_returnsRecipeById() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.getRecipeById(id)).thenReturn(sampleRecipe(id));

        mockMvc.perform(get("/api/recipes/recipe/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.description").value("Tasty bread"))
                .andExpect(jsonPath("$.ingredients[0].amount").value(500));
    }

    @Test
    void getRecipe_withInvalidUuid_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/recipes/recipe/{id}", "not-a-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addRecipe_returnsCreatedRecipe() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe input = sampleRecipe(null);
        when(service.addRecipe(any(Recipe.class))).thenReturn(sampleRecipe(id));

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Bread"));

        verify(service).addRecipe(any(Recipe.class));
    }
}
