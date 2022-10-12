package app.shop.onlineshop.controllers.api.products;

import app.shop.onlineshop.entities.products.Color;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.services.products.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    private ColorService service;


    @GetMapping("/")
    public ServiceResponse<Color> getAll(){
        try{
            List<Color> result = service.getAll();
            return new ServiceResponse<Color>(ResponseStatus.SUCCESS,result);
        }catch (Exception e){
            return new ServiceResponse<Color>(e);
        }
    }

    @GetMapping("/{id}")
    public ServiceResponse<Color> search(@PathVariable long id){
        try{
            Color result = service.getById(id);
            return new ServiceResponse<Color>(ResponseStatus.SUCCESS,result);
        }catch (Exception e){
            return new ServiceResponse<Color>(e);
        }
    }

    @GetMapping("/getAll")
    public ServiceResponse<Color> getAll(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber){
        try{
            List<Color> result = service.getAll(pageSize,pageNumber);
            long totalCount = service.getAllCount();
            return new ServiceResponse<Color>(ResponseStatus.SUCCESS,result,totalCount);
        }catch (Exception e){
            return new ServiceResponse<Color>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Color> add(@RequestBody Color data){
        try {
            Color result = service.add(data);
            return new ServiceResponse<Color>(ResponseStatus.SUCCESS, result);
        }catch (Exception e){
            return new ServiceResponse<Color>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Color> update(@RequestBody Color data){
        try {
            Color result = service.update(data);
            return new ServiceResponse<Color>(ResponseStatus.SUCCESS, result);
        }catch (Exception e){
            return new ServiceResponse<Color>(e);
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

