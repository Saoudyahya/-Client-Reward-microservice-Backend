package com.example.clientMicroservice.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reward_type_id", nullable = false)
    private RewardType rewardType;

    @Column
    private int PointsEarned ;

    @Column
    private Date DateEarned;



    public Reward() {
    }

    public Reward(Long id, Client client, RewardType rewardType, int pointsEarned, Date dateEarned) {
        this.id = id;
        this.client = client;
        this.rewardType = rewardType;
        this.PointsEarned = pointsEarned;
        this.DateEarned = dateEarned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public int getPointsEarned() {
        return PointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        PointsEarned = pointsEarned;
    }

    public Date getDateEarned() {
        return DateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        DateEarned = dateEarned;
    }
}