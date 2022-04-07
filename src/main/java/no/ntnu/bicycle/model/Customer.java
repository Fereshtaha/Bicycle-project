package no.ntnu.bicycle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDate dob;
    private Integer age;
    private boolean active = true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role",
            joinColumns = @JoinColumn(name="customer_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new LinkedHashSet<>();

    //@OneToMany
    //@JsonIgnore
    //private Set<CustomerOrder> orders = new HashSet<>();

    public Customer() {

    }

    public Customer(String name, String password, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
        LocalDate today = LocalDate.now(); // Today's date is 10th Jan 2022
        Period p = Period.between(dob, today);
        this.age = p.getYears();

    }

    /**
     * Getters and setters
     * @return values
     */

    @JsonIgnore
    public boolean isValid() {
        return !"".equals(name) && age >0;
    }

    public int getId() {
        return id;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void updateAge(){
        LocalDate today = LocalDate.now();
        Period p = Period.between(this.dob, today);
        this.age = p.getYears();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }

    /**
     * Add a role to the user
     *
     * @param role Role to add
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     * Adding an order to all the orders
     * @param order the order to add
     */

    //public void addOrder(CustomerOrder order) {
    //    orders.add(order);
    //}
}
