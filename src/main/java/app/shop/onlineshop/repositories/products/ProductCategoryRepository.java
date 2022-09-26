package app.shop.onlineshop.repositories.products;

import app.shop.onlineshop.entities.products.ProductCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory,Long> {
    List<ProductCategory> findAllByEnableIsTrue(Sort sort);
}

