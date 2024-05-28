package ru.yuubi.weather_viewer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Login")
    private String login;

    @Column(name = "Password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}


