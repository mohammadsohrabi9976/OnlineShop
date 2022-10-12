package app.shop.onlineshop.helper.uiModels;

import app.shop.onlineshop.entities.people.User;
import app.shop.onlineshop.enums.UserRole;

public class UserVM {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String newPassword;
    private String email;
    private UserRole role;
    private boolean enable;
    private String token;
    private String fullName;
    private long customerId;
    private CustomerVM customer;

    public UserVM() {
    }


    public UserVM(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setEnable(user.isEnable());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setRole(user.getRole());
        setUsername(user.getUsername());
        setFullName(getFirstName() + " " + getLastName());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public CustomerVM getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerVM customer) {
        this.customer = customer;
    }
}
