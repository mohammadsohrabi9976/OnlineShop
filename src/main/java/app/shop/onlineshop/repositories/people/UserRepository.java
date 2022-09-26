package app.shop.onlineshop.repositories.people;

import app.shop.onlineshop.entities.people.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findFirstByUsernameAndPassword(String username, String password);
    User findFirstByUsername(String username);
    long countByEnableIsTrue();
}
