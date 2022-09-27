package app.shop.onlineshop.services.products;

import app.shop.onlineshop.entities.products.Size;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.repositories.products.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    @Autowired
    private SizeRepository repository;

    public List<Size> getAll() {
        return repository.findAll();
    }

    public Size getById(long id){
        Optional<Size> data = repository.findById(id);
        if(data.isPresent()) return data.get();
        return null;
    }

    public List<Size> getAll(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Size> all = repository.findAll(page);
        return all.toList();
    }

    public long getAllCount() {
        return repository.count();
    }
    public Size add(Size data){
        return repository.save(data);
    }

    public Size update(Size data) throws DataNotFoundException {
        Size oldData = getById(data.getId());
        if(oldData == null){
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setTitle(data.getTitle());
        oldData.setDescription(data.getDescription());
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        Size oldData = getById(id);
        if(oldData == null){
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        repository.deleteById(id);
        return true;
    }

}

