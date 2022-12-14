package app.shop.onlineshop.services.products;

import app.shop.onlineshop.entities.products.Color;
import app.shop.onlineshop.entities.products.Feature;
import app.shop.onlineshop.entities.products.Product;
import app.shop.onlineshop.entities.products.Size;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.helper.uiModels.ProductVM;
import app.shop.onlineshop.repositories.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductCategoryService productCategoryService;

    public List<Product> findAllByCategory(long categoryId) {
        return repository.findAllByCategory(categoryId);
    }

    public List<Product> search(String keyword) {
        return repository.findAllByEnableIsTrueAndTitleContainsOrDescriptionContains(keyword);
    }

    public List<Product> getAll(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Product> all = repository.findAll(page);
        return all.toList();
    }

    public long getAllCount() {
        return repository.count();
    }

    public long getExistsCount() {
        return repository.countByExistsIsTrue();
    }

    public long getEnableCount() {
        return repository.countByEnableIsTrue();
    }

    public List<ProductVM> getAllByCategoryId(long categoryId, Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Product> all = repository.findAllByCategory(categoryId, page);
        List<ProductVM> vmList = new ArrayList<>();
        all.toList().forEach(x -> vmList.add(new ProductVM(x)));
        return vmList;
    }

    public long getAllCountByCategoryId(long categoryId) {
        return repository.countByCategoryId(categoryId);
    }

    public Product getById(long id) {
        Optional<Product> data = repository.findById(id);
        if (data.isPresent()) return data.get();
        return null;
    }

    public List<ProductVM> findTop6ByOrderByAddDateDesc() {
        List<ProductVM> vmList = new ArrayList<>();
        List<Product> top6 = repository.findTop6ByOrderByAddDateDesc();
        top6.forEach(x -> vmList.add(new ProductVM(x)));
        return vmList;
    }

    public List<ProductVM> findTop6ByOrderByVisitCountDesc() {
        List<ProductVM> vmList = new ArrayList<>();
        List<Product> top6 = repository.findTop6ByOrderByVisitCountDesc();
        top6.forEach(x -> vmList.add(new ProductVM(x)));
        return vmList;
    }

    public Product add(ProductVM vm) {
        Product data = vm.convert();
        if (vm.getFeatures() != null)
            vm.getFeatures().forEach(x -> data.addFeature(featureService.getById(x)));
        if (vm.getColors() != null)
            vm.getColors().forEach(x -> data.addColor(colorService.getById(x)));
        if (vm.getSizes() != null)
            vm.getSizes().forEach(x -> data.addSize(sizeService.getById(x)));
        data.setCategory(productCategoryService.getById(vm.getCategoryId()));
        data.setAddDate(new Date());
        return repository.save(data);
    }

    public Product update(ProductVM data) throws DataNotFoundException {
        Product oldData = getById(data.getId());
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setTitle(data.getTitle());
        oldData.setEnable(data.isEnable());
        oldData.setDescription(data.getDescription());
        oldData.setImage(data.getImage());
        oldData.setExists(data.isExists());
        oldData.setPrice(data.getPrice());
        if (data.getColors() != null) {
            for (long colorId : data.getColors()) {
                if (!oldData.getColors().stream().map(x -> x.getId()).anyMatch(z -> z == colorId))
                    oldData.addColor(colorService.getById(colorId));
            }
            for (Color color : oldData.getColors()) {
                if (!data.getColors().stream().anyMatch(x -> x == color.getId())) {
                    oldData.removeColor(color.getId());
                }
            }
        }
        if (data.getSizes() != null) {
            for (long sizeId : data.getSizes()) {
                if (!oldData.getSizes().stream().map(x -> x.getId()).anyMatch(z -> z == sizeId))
                    oldData.addSize(sizeService.getById(sizeId));
            }
            for (Size size : oldData.getSizes()) {
                if (!data.getSizes().stream().anyMatch(x -> x == size.getId())) {
                    oldData.removeSize(size.getId());
                }
            }
        }
        if (data.getFeatures() != null) {
            for (long featureId : data.getFeatures()) {
                if (!oldData.getFeatures().stream().map(x -> x.getId()).anyMatch(z -> z == featureId))
                    oldData.addFeature(featureService.getById(featureId));
            }
            for (Feature feature : oldData.getFeatures()) {
                if (!data.getFeatures().stream().anyMatch(x -> x == feature.getId())) {
                    oldData.removeFeature(feature.getId());
                }
            }
        }
        return repository.save(oldData);
    }

    public boolean deleteById(long id) throws DataNotFoundException {
        Product oldData = getById(id);
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        List<Long> deletingFeatures = new ArrayList<>();
        oldData.getFeatures().forEach(x -> deletingFeatures.add(x.getId()));
        repository.deleteById(id);
        deletingFeatures.forEach(x -> {
            try {
                featureService.deleteById(x);
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    public Product increaseVisitCount(long id) throws DataNotFoundException {
        Product oldData = getById(id);
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + id + " not found");
        }
        oldData.setVisitCount(oldData.getVisitCount() + 1);
        return repository.save(oldData);
    }

}

