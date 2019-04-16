package com.petia.dollhouse.domain.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User extends EntityWithStatus implements UserDetails {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer", targetEntity = Reservation.class)
    private List<Reservation> customerReservations;

    @OneToMany(mappedBy = "employee", targetEntity = Reservation.class)
    private List<Reservation> employeeReservations;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> authorities;

    @ManyToOne(targetEntity = Office.class)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;

    @ManyToOne(targetEntity = DHService.class)
    @JoinColumn(name = "employeeService_id", referencedColumnName = "id")
    private DHService employeeService;

    @Column(name = "image_url")
    private String imageUrl;

    public User() {
        authorities = new HashSet<>();
        customerReservations = new ArrayList<>();
        employeeReservations = new ArrayList<>();

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    public List<Reservation> getCustomerReservations() {
        return customerReservations;
    }

    public void setCustomerReservations(List<Reservation> customerReservations) {
        this.customerReservations = customerReservations;
    }

    public List<Reservation> getEmployeeReservations() {
        return employeeReservations;
    }

    public void setEmployeeReservations(List<Reservation> employeeReservations) {
        this.employeeReservations = employeeReservations;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public DHService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(DHService employeeService) {
        this.employeeService = employeeService;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
