package ru.yuubi.weather_viewer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    private String id;
    @Column(name = "UserId")
    private int userId;

    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;


    public Session(String id, int userId, LocalDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }
}
