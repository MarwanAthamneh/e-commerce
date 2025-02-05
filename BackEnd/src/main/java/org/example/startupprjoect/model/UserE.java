package org.example.startupprjoect.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;


 @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
 @JoinTable(name="user_roles",joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"),
 inverseJoinColumns = @JoinColumn(name = "role_id" ,referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>() ;

 @OneToOne(mappedBy = "userE",cascade = CascadeType.ALL)
 @JsonManagedReference
 private Cart cart;


}

