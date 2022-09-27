package app.shop.onlineshop.services.products;

import app.shop.onlineshop.entities.products.Color;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.repositories.products.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {

    @Autowired
    private ColorRepository repository;

    public Color getById(long id) {
        Optional<Color> data = repository.findById(id);
        if (data.isPresent()) return data.get();
        return null;
    }

    public List<Color> getAll() {
        return repository.findAll();
    }

    public List<Color> getAll(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Color> all = repository.findAll(page);
        return all.toList();
    }

    public long getAllCount() {
        return repository.count();
    }

    public Color add(Color data) {
        return repository.save(data);
    }

    public Color update(Color data) throws DataNotFoundException {
        Color oldData = getById(data.getId());
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setValue(data.getValue());
        oldData.setName(data.getName());
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        Color oldData = getById(id);
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        repository.deleteById(id);
        return true;
    }

}
