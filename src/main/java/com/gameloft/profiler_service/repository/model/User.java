package com.gameloft.profiler_service.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private String id;
    private String credential;
    private Integer level;
    private String country;
    private String language;
    private Instant birthDate;
    private String gender;
    private Integer totalSpent;
    private Integer totalRefund;
    private Integer totalTransactions;
    private Instant lastPurchase;
    private String xp;
    private Instant lastSession;
    private String customField;

    @OneToOne(mappedBy = "user")
    private Clan clan;

    @ManyToMany
    @JoinTable(
            name = "user_device_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private List<Device> devices;

    @OneToOne(mappedBy = "user")
    private Inventory inventory;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}

