package app.shop.onlineshop.controllers.api.products;

import app.shop.onlineshop.entities.products.Feature;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.services.products.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feature")
public class FeatureController {

    @Autowired
    private FeatureService service;

    @GetMapping("/{id}")
    public ServiceResponse<Feature> search(@PathVariable long id){
        try{
            Feature result = service.getById(id);
            return new ServiceResponse<Feature>(ResponseStatus.SUCCESS,result);
        }catch (Exception e){
            return new ServiceResponse<Feature>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Feature> add(@RequestBody Feature data){
        try {
            Feature result = service.add(data);
            return new ServiceResponse<Feature>(ResponseStatus.SUCCESS, result);
        }catch (Exception e){
            return new ServiceResponse<Feature>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Feature> update(@RequestBody Feature data){
        try {
            Feature result = service.update(data);
            return new ServiceResponse<Feature>(ResponseStatus.SUCCESS, result);
        }catch (Exception e){
            return new ServiceResponse<Feature>(e);
        }
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Boolean> delete(@PathVariable long id){
        try {
            boolean result = service.deleteById(id);
            return new ServiceResponse<Boolean>(ResponseStatus.SUCCESS, result);
        }catch (Exception e){
            return new ServiceResponse<Boolean>(e);
        }
    }

}

