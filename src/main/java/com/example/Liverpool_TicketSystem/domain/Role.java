package com.example.Liverpool_TicketSystem.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles") // Chỉ định tên bảng là roles
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    // 1 role có nhiều user sử dụng (nhiều user có thể có cùng 1 role)
    // 1 user chỉ có 1 role
    @OneToMany (mappedBy = "role")

    // Một đối tượng Role sẽ chứa danh sách tất cả các User có cùng role đó.
    // Vì 1 role có thể có nhiều user, nên ta dùng @OneToMany để ánh xạ quan hệ này.
    private List<User> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", description=" + description + ", users=" + users + "]";
    }

    
    
}
