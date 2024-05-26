package com.example.clientMicroservice.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reward_types")
public class RewardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;



    public RewardType(Long id, String name, List<Reward> rewards) {
        this.id = id;
        this.name = name;
    }

    public RewardType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}