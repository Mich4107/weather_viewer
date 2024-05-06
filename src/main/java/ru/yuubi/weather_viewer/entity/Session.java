package ru.yuubi.weather_viewer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    private String id;

    @Column(name = "UserId")
    private int userId;

    @Column(name = "ExpiresAt")
    private String expiresAt;

    public Session() {
    }

    public Session(String id, int userId, String expiresAt) {
        this.id = id;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
