package app.shop.onlineshop.controllers.api.site;

import app.shop.onlineshop.entities.site.Blog;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.services.site.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService service;

    @GetMapping("")
    public ServiceResponse<Blog> search(@RequestParam String keyword) {
        try {
            List<Blog> result = service.search(keyword);
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @GetMapping("/getAll")
    public ServiceResponse<Blog> getAll(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber) {
        try {
            List<Blog> result = service.getAll(pageSize, pageNumber);
            long totalCount = service.getAllCount();
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @GetMapping("/getAllData")
    public ServiceResponse<Blog> getAllData(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber) {
        try {
            List<Blog> result = service.getAllData(pageSize, pageNumber);
            long totalCount = service.getAllCountData();
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @GetMapping("/info/{id}")
    public ServiceResponse<Blog> getInfo(@PathVariable long id) {
        try {
            Blog result = service.getById(id);
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Blog> add(@RequestBody Blog data) {
        try {
            Blog result = service.add(data);
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Blog> update(@RequestBody Blog data) {
        try {
            Blog result = service.update(data);
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Boolean> delete(@PathVariable long id) {
        try {
            boolean result = service.deleteById(id);
            return new ServiceResponse<Boolean>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Boolean>(e);
        }
    }

    @PutMapping("/increaseVisit/{id}")
    public ServiceResponse<Blog> update(@PathVariable long id) {
        try {
            Blog result = service.increaseVisitCount(id);
            return new ServiceResponse<Blog>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Blog>(e);
        }
    }
}

