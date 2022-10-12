package app.shop.onlineshop.controllers.api.people;

import app.shop.onlineshop.config.JwtTokenUtil;
import app.shop.onlineshop.entities.people.Customer;
import app.shop.onlineshop.entities.people.User;
import app.shop.onlineshop.enums.UserRole;
import app.shop.onlineshop.helper.exceptions.JwtTokenException;
import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.helper.uiModels.CustomerVM;
import app.shop.onlineshop.helper.uiModels.UserVM;
import app.shop.onlineshop.services.people.CustomerService;
import app.shop.onlineshop.services.people.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ServiceResponse<UserVM> login(@RequestBody User user) {
        User userData = service.auth(user.getUsername(), user.getPassword());
        if (userData == null)
            return new ServiceResponse<UserVM>(ResponseStatus.FAILED, "Incorrect username or password");

        UserVM userVM = new UserVM(userData);

        String token = jwtTokenUtil.generateToken(userVM);
        userVM.setToken(token);
        return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, userVM);
    }

    @GetMapping("/getAll")
    public ServiceResponse<UserVM> getAll(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber) {
        try {
            List<User> result = service.getAll(pageSize, pageNumber);
            List<UserVM> resultVM = new ArrayList<>();
            result.stream().forEach(x -> resultVM.add(new UserVM(x)));
            long totalCount = service.getAllCount();
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, resultVM, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
        }
    }

    @GetMapping("/{id}")
    public ServiceResponse<UserVM> search(@PathVariable long id) {
        try {
            User result = service.getById(id);
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, new UserVM(result));
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
        }
    }

    @GetMapping("/getUserInfo")
    public ServiceResponse<UserVM> getUserInfo(HttpServletRequest request) {
        try {
            String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer "))
                throw new JwtTokenException("request token header does not set");

            String token = requestTokenHeader.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);

            if (username == null)
                throw new JwtTokenException("username can not resolve");

            UserVM user = new UserVM(service.getByUsername(username));

            if (user.getRole() != UserRole.ADMIN) {
                Customer customer = customerService.getByUserId(user.getId());
                user.setCustomerId(customer.getId());
                user.setCustomer(new CustomerVM(customer));
            }
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, user);
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<UserVM> add(@RequestBody User data) {
        try {
            User result = service.add(data);
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, new UserVM(result));
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<UserVM> update(@RequestBody User data) {
        try {
            User result = service.update(data);
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, new UserVM(result));
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
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

    @PutMapping("/changePass")
    public ServiceResponse<UserVM> changePassword(@RequestBody UserVM data) {
        try {
            User result = service.changePassword(data.getId(), data.getPassword(), data.getNewPassword());
            return new ServiceResponse<UserVM>(ResponseStatus.SUCCESS, new UserVM(result));
        } catch (Exception e) {
            return new ServiceResponse<UserVM>(e);
        }
    }
}

