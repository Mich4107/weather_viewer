package ru.yuubi.weather_viewer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    private String id;

    @Column(name = "UserId")
    private int userId;


}
