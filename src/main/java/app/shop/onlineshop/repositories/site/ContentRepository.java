package app.shop.onlineshop.repositories.site;

import app.shop.onlineshop.entities.site.Content;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends PagingAndSortingRepository<Content, Long> {
    Content findFirstByKey(String key);

    @Override
    List<Content> findAll();
}

