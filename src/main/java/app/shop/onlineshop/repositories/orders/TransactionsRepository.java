package app.shop.onlineshop.repositories.orders;

import app.shop.onlineshop.entities.orders.Transactions;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {
    Transactions findByAuthority(String authority);
}
