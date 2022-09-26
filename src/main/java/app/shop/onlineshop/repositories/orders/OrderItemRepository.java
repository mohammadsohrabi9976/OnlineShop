package app.shop.onlineshop.repositories.orders;

import app.shop.onlineshop.entities.orders.OrderItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem,Long> {
}
