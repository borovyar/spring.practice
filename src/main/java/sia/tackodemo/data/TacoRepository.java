package sia.tackodemo.data;

import org.springframework.data.repository.CrudRepository;
import sia.tackodemo.domain.entity.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
