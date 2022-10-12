package app.shop.onlineshop.controllers.api.orders;

import app.shop.onlineshop.config.JwtTokenUtil;
import app.shop.onlineshop.entities.orders.Invoice;
import app.shop.onlineshop.entities.people.Customer;
import app.shop.onlineshop.enums.UserRole;
import app.shop.onlineshop.helper.exceptions.JwtTokenException;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.helper.uiModels.UserVM;
import app.shop.onlineshop.services.orders.InvoiceService;
import app.shop.onlineshop.services.people.CustomerService;
import app.shop.onlineshop.services.people.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/find")
    public ServiceResponse<Invoice> find(@RequestParam long cid,
                                         @RequestParam Integer pageSize,
                                         @RequestParam Integer pageNumber,
                                         HttpServletRequest request) {
        try {
            UserVM userVM = getUserVMFromToken(request);
            if (userVM.getRole() != UserRole.ADMIN) {
                Customer customer = customerService.getByUserId(userVM.getId());
                if (customer.getId() != cid)
                    throw new Exception("You can see only your invoices");
            }
            List<Invoice> result = service.findByCustomer(cid, pageSize, pageNumber);
            return new ServiceResponse<Invoice>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Invoice>(e);
        }
    }

    @GetMapping("/getInfo/{id}")
    public ServiceResponse<Invoice> search(@PathVariable long id, HttpServletRequest request) {
        try {
            Invoice result = service.getById(id);
            UserVM userVM = getUserVMFromToken(request);
            if (userVM.getRole() != UserRole.ADMIN) {
                Customer customer = customerService.getByUserId(userVM.getId());
                if (customer.getId() != result.getCustomer().getId())
                    throw new Exception("You can see only your invoices");
            }
            return new ServiceResponse<Invoice>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Invoice>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Invoice> add(@RequestBody Invoice data) {
        try {
            Invoice result = service.add(data);
            return new ServiceResponse<Invoice>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Invoice>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Invoice> update(@RequestBody Invoice data) {
        try {
            Invoice result = service.update(data);
            return new ServiceResponse<Invoice>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Invoice>(e);
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

    private UserVM getUserVMFromToken(HttpServletRequest request) throws JwtTokenException {
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer "))
            throw new JwtTokenException("request token header does not set");

        String token = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        if (username == null)
            throw new JwtTokenException("username can not resolve");

        UserVM userVM = new UserVM(userService.getByUsername(username));
        return userVM;
    }

}
