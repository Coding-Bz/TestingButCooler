package ch.tbz.recipe.planner.service;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeEntityMapper mapper;

    @Mock
    private RecipeRepository repository;

    @InjectMocks
    private RecipeService service;

    @Test
    void updateRecipe_unknownId_throwsNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateRecipe(id, new Recipe()))
                .isInstanceOf(ResponseStatusException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateRecipe_savesRecipeWithIdFromUrl() {
        UUID id = UUID.randomUUID();
        Recipe input = new Recipe();
        RecipeEntity entity = new RecipeEntity();
        RecipeEntity saved = new RecipeEntity();
        Recipe expected = new Recipe();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.domainToEntity(input)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.entityToDomain(saved)).thenReturn(expected);

        Recipe result = service.updateRecipe(id, input);

        assertThat(result).isSameAs(expected);
        assertThat(input.getId()).isEqualTo(id);
    }
}
