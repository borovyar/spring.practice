package sia.tackodemo.data;

import org.springframework.data.repository.CrudRepository;
import sia.tackodemo.domain.entity.security.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
