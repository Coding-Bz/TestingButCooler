package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class IngredientEntityMapperTest {

    private final IngredientEntityMapper mapper = org.mapstruct.factory.Mappers.getMapper(IngredientEntityMapper.class);

    @Test
    void entityToDomain_mapsAllFields() {
        UUID id = UUID.randomUUID();
        IngredientEntity entity = new IngredientEntity(id, "Milk", "cold", Unit.LITRE, 2);

        Ingredient result = mapper.entityToDomain(entity);

        assertSoftly(softly -> {
            softly.assertThat(result.getId()).isEqualTo(id);
            softly.assertThat(result.getName()).isEqualTo("Milk");
            softly.assertThat(result.getComment()).isEqualTo("cold");
            softly.assertThat(result.getUnit()).isEqualTo(Unit.LITRE);
            softly.assertThat(result.getAmount()).isEqualTo(2);
        });
    }

    @Test
    void domainToEntity_mapsAllFields() {
        UUID id = UUID.randomUUID();
        Ingredient domain = new Ingredient(id, "Eggs", "free range", Unit.PIECE, 6);

        IngredientEntity result = mapper.domainToEntity(domain);

        assertSoftly(softly -> {
            softly.assertThat(result.getId()).isEqualTo(id);
            softly.assertThat(result.getName()).isEqualTo("Eggs");
            softly.assertThat(result.getComment()).isEqualTo("free range");
            softly.assertThat(result.getUnit()).isEqualTo(Unit.PIECE);
            softly.assertThat(result.getAmount()).isEqualTo(6);
        });
    }

    @Test
    void listMappings_mapEveryElement() {
        List<IngredientEntity> entities = List.of(
                new IngredientEntity(UUID.randomUUID(), "A", null, Unit.GRAMM, 1),
                new IngredientEntity(UUID.randomUUID(), "B", null, Unit.KILOGRAMM, 2));

        List<Ingredient> domains = mapper.entitiesToDomains(entities);
        List<IngredientEntity> back = mapper.domainsToEntities(domains);

        assertSoftly(softly -> {
            softly.assertThat(domains).extracting(Ingredient::getName).containsExactly("A", "B");
            softly.assertThat(back).isEqualTo(entities);
        });
    }

    @Test
    void nullInput_returnsNull() {
        assertSoftly(softly -> {
            softly.assertThat(mapper.entityToDomain(null)).isNull();
            softly.assertThat(mapper.domainToEntity(null)).isNull();
        });
    }
}
