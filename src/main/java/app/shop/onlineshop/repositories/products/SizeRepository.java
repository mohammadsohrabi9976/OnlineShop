package app.shop.onlineshop.repositories.products;

import app.shop.onlineshop.entities.products.Size;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends PagingAndSortingRepository<Size,Long> {

    @Override
    List<Size> findAll();
}

