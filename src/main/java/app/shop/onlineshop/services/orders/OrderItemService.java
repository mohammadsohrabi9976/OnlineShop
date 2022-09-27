package app.shop.onlineshop.services.orders;

import app.shop.onlineshop.entities.orders.OrderItem;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.repositories.orders.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    public OrderItem getById(long id){
        Optional<OrderItem> data = repository.findById(id);
        if(data.isPresent()) return data.get();
        return null;
    }

    public OrderItem add(OrderItem data){
        return repository.save(data);
    }

    public OrderItem update(OrderItem data) throws DataNotFoundException {
        OrderItem oldData = getById(data.getId());
        if(oldData == null){
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setCount(data.getCount());
        oldData.setPrice(data.getPrice());
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        OrderItem oldData = getById(id);
        if(oldData == null){
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        repository.deleteById(id);
        return true;
    }

}