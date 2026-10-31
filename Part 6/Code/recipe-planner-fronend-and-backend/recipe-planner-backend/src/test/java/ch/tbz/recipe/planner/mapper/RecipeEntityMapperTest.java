package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class RecipeEntityMapperTest {

    private final RecipeEntityMapper mapper = org.mapstruct.factory.Mappers.getMapper(RecipeEntityMapper.class);

    @Test
    void entityToDomain_mapsRecipeAndNestedIngredients() {
        UUID recipeId = UUID.randomUUID();
        UUID ingredientId = UUID.randomUUID();
        RecipeEntity entity = new RecipeEntity(recipeId, "Pasta", "Quick pasta", "http://img/pasta.png",
                List.of(new IngredientEntity(ingredientId, "Spaghetti", "al dente", Unit.GRAMM, 250)));

        Recipe result = mapper.entityToDomain(entity);

        assertSoftly(softly -> {
            softly.assertThat(result.getId()).isEqualTo(recipeId);
            softly.assertThat(result.getName()).isEqualTo("Pasta");
            softly.assertThat(result.getDescription()).isEqualTo("Quick pasta");
            softly.assertThat(result.getImageUrl()).isEqualTo("http://img/pasta.png");
            softly.assertThat(result.getIngredients()).hasSize(1);
            softly.assertThat(result.getIngredients().get(0).getId()).isEqualTo(ingredientId);
            softly.assertThat(result.getIngredients().get(0).getName()).isEqualTo("Spaghetti");
            softly.assertThat(result.getIngredients().get(0).getUnit()).isEqualTo(Unit.GRAMM);
            softly.assertThat(result.getIngredients().get(0).getAmount()).isEqualTo(250);
        });
    }

    @Test
    void domainToEntity_mapsRecipeAndNestedIngredients() {
        UUID recipeId = UUID.randomUUID();
        Recipe domain = new Recipe(recipeId, "Salad", "Fresh", "http://img/salad.png",
                List.of(new Ingredient(UUID.randomUUID(), "Tomato", "ripe", Unit.PIECE, 3)));

        RecipeEntity result = mapper.domainToEntity(domain);

        assertSoftly(softly -> {
            softly.assertThat(result.getId()).isEqualTo(recipeId);
            softly.assertThat(result.getName()).isEqualTo("Salad");
            softly.assertThat(result.getDescription()).isEqualTo("Fresh");
            softly.assertThat(result.getImageUrl()).isEqualTo("http://img/salad.png");
            softly.assertThat(result.getIngredients()).hasSize(1);
            softly.assertThat(result.getIngredients().get(0).getName()).isEqualTo("Tomato");
            softly.assertThat(result.getIngredients().get(0).getUnit()).isEqualTo(Unit.PIECE);
            softly.assertThat(result.getIngredients().get(0).getAmount()).isEqualTo(3);
        });
    }

    @Test
    void roundTrip_keepsData() {
        Recipe original = new Recipe(UUID.randomUUID(), "Soup", "Warm", null,
                List.of(new Ingredient(UUID.randomUUID(), "Water", null, Unit.DECILITRE, 5)));

        Recipe result = mapper.entityToDomain(mapper.domainToEntity(original));

        assertSoftly(softly -> softly.assertThat(result).isEqualTo(original));
    }

    @Test
    void nullInput_returnsNull() {
        assertSoftly(softly -> {
            softly.assertThat(mapper.entityToDomain(null)).isNull();
            softly.assertThat(mapper.domainToEntity(null)).isNull();
        });
    }
}
