package app.shop.onlineshop.controllers.api.products;

import app.shop.onlineshop.entities.products.Product;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.helper.uiModels.ProductVM;
import app.shop.onlineshop.services.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public ServiceResponse<Product> search(@RequestParam String keyword) {
        try {
            List<Product> result = service.search(keyword);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @GetMapping("/getAll")
    public ServiceResponse<Product> getAll(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber) {
        try {
            List<Product> result = service.getAll(pageSize, pageNumber);
            long totalCount = service.getAllCount();
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @GetMapping("/getAll/{cid}")
    public ServiceResponse<ProductVM> getAll(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber,
            @PathVariable long cid) {
        try {
            List<ProductVM> result = service.getAllByCategoryId(cid, pageSize, pageNumber);
            long totalCount = service.getAllCountByCategoryId(cid);
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }


    @GetMapping("/find")
    public ServiceResponse<Product> find(@RequestParam long cid) {
        try {
            List<Product> result = service.findAllByCategory(cid);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @GetMapping("/info/{id}")
    public ServiceResponse<ProductVM> getById(@PathVariable long id) {
        try {
            Product result = service.getById(id);
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, new ProductVM(result));
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @GetMapping("/newProducts")
    public ServiceResponse<ProductVM> newProducts() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByAddDateDesc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @GetMapping("/popularProducts")
    public ServiceResponse<ProductVM> popularProducts() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByVisitCountDesc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Product> add(@RequestBody ProductVM data) {
        try {
            Product result = service.add(data);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Product> update(@RequestBody ProductVM data) {
        try {
            Product result = service.update(data);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
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

}
