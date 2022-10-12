package app.shop.onlineshop.controllers.api.site;

import app.shop.onlineshop.helper.ui.ResponseStatus;
import app.shop.onlineshop.helper.ui.ServiceResponse;
import app.shop.onlineshop.helper.uiModels.DashboardVM;
import app.shop.onlineshop.services.orders.InvoiceService;
import app.shop.onlineshop.services.people.CustomerService;
import app.shop.onlineshop.services.people.UserService;
import app.shop.onlineshop.services.products.ProductCategoryService;
import app.shop.onlineshop.services.products.ProductService;
import app.shop.onlineshop.services.site.BlogService;
import app.shop.onlineshop.services.site.NavService;
import app.shop.onlineshop.services.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private NavService navService;

    @Autowired
    private SliderService sliderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("")
    public ServiceResponse<DashboardVM> get() {
        try {
            DashboardVM result = new DashboardVM();
            result.setNavigations(navService.getAllCount());
            result.setSliders(sliderService.getAllCount());
            result.setActiveBlog(blogService.getAllCountData());
            result.setAllBlog(blogService.getAllCount());
            result.setCategories(categoryService.getAllCount());
            result.setProducts(productService.getAllCount());
            result.setExistsProducts(productService.getExistsCount());
            result.setEnableProducts(productService.getEnableCount());
            result.setUsers(userService.getAllCount());
            result.setActiveUsers(userService.getEnableCount());
            result.setCustomers(customerService.getAllCount());
            result.setInvoices(invoiceService.getAllCount());
            result.setPayedInvoices(invoiceService.getPayedCount());
            return new ServiceResponse<DashboardVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<DashboardVM>(e);
        }
    }

}
