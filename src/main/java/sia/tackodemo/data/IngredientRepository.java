package sia.tackodemo.data;

import org.springframework.data.repository.CrudRepository;
import sia.tackodemo.domain.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
