package com.gameloft.profiler_service.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity(name = "user_devices")
public class Device {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "devices")
    @JsonIgnore
    private List<User> users;

    private String model;

    private String carrier;

    private String firmware;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
