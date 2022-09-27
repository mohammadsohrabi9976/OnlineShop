package app.shop.onlineshop.services.site;

import app.shop.onlineshop.entities.site.Blog;
import app.shop.onlineshop.enums.BlogStatus;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.repositories.site.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository repository;

    public List<Blog> search(String keyword){
        return repository.findAllByTitleContainsOrDescriptionContains(keyword);
    }

    public Blog getById(long id){
        Optional<Blog> data = repository.findById(id);
        if(data.isPresent()) return data.get();
        return null;
    }


    public List<Blog> getAll(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("publishDate"));
        Page<Blog> all = repository.findAll(page);
        return all.toList();
    }

    public long getAllCount() {
        return repository.count();
    }

    public List<Blog> getAllData(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("publishDate").descending());
        Page<Blog> all = repository.findAllByStatusAndPublishDateLessThanEqual(BlogStatus.PUBLISHED,new Date(),page);
        return all.toList();
    }

    public long getAllCountData() {
        return repository.countByStatusAndPublishDateLessThanEqual(BlogStatus.PUBLISHED,new Date());
    }



    public Blog add(Blog data) throws Exception {
        if(data.getTitle() == null || data.getTitle().equals(""))
            throw new Exception("please fill title field");
        data.setVisitCount(0);
        return repository.save(data);
    }

    public Blog update(Blog data) throws DataNotFoundException {
        Blog oldData = getById(data.getId());
        if(oldData == null){
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setTitle(data.getTitle());
        oldData.setDescription(data.getDescription());
        oldData.setImage(data.getImage());
        oldData.setStatus(data.getStatus());
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        Blog oldData = getById(id);
        if(oldData == null){
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        repository.deleteById(id);
        return true;
    }

    public Blog increaseVisitCount(long id) throws DataNotFoundException {
        Blog oldData = getById(id);
        if(oldData == null){
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        oldData.setVisitCount(oldData.getVisitCount() + 1);
        return repository.save(oldData);
    }

}

