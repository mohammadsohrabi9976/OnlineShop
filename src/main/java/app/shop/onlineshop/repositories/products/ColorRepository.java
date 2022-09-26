package app.shop.onlineshop.repositories.products;

import app.shop.onlineshop.entities.products.Color;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color,Long> {
    @Override
    List<Color> findAll();
}

