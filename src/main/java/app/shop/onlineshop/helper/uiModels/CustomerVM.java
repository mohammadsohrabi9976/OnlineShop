package app.shop.onlineshop.helper.uiModels;

import app.shop.onlineshop.entities.people.Customer;
import app.shop.onlineshop.entities.people.User;
import app.shop.onlineshop.enums.UserRole;

public class CustomerVM {
    private long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String tel;
    private String address;
    private String postalCode;
    private String email;
    private String username;
    private String password;
    private long userId;

    public CustomerVM() {
    }

    public CustomerVM(Customer customer) {
        setId(customer.getId());
        setFirstName(customer.getFirstName());
        setLastName(customer.getLastName());
        setMobile(customer.getMobile());
        setTel(customer.getTel());
        setAddress(customer.getAddress());
        setPostalCode(customer.getPostalCode());
        setEmail(customer.getEmail());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public Customer getCustomerInfo() {
        Customer customer = new Customer();
        customer.setId(getId());
        customer.setAddress(getAddress());
        customer.setEmail(getEmail());
        customer.setFirstName(getFirstName());
        customer.setLastName(getLastName());
        customer.setMobile(getMobile());
        customer.setPostalCode(getPostalCode());
        customer.setTel(getTel());
        return customer;
    }

    public User getUserInfo() {
        User user = new User();
        user.setId(getUserId());
        user.setEmail(getEmail());
        user.setEnable(true);
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setPassword(getPassword());
        user.setUsername(getUsername());
        user.setRole(UserRole.USER);
        return user;
    }

}
