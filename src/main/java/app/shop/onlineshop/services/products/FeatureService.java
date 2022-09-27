package app.shop.onlineshop.services.products;

import app.shop.onlineshop.entities.products.Feature;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.repositories.products.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository repository;

    public Feature getById(long id){
        Optional<Feature> data = repository.findById(id);
        if(data.isPresent()) return data.get();
        return null;
    }

    public Feature add(Feature data){
        return repository.save(data);
    }

    public Feature update(Feature data) throws DataNotFoundException {
        Feature oldData = getById(data.getId());
        if(oldData == null){
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setValue(data.getValue());
        oldData.setKey(data.getKey());
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        Feature oldData = getById(id);
        if(oldData == null){
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        repository.deleteById(id);
        return true;
    }

}