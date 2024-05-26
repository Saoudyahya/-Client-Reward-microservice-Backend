package com.example.clientMicroservice.service;

import com.example.clientMicroservice.models.Reward;
import com.example.clientMicroservice.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    public Optional<Reward> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }

    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    public Reward updateReward(Long id, Reward updatedReward) {
        return rewardRepository.findById(id).map(existingReward -> {
            existingReward.setClient(updatedReward.getClient());
            existingReward.setClient(updatedReward.getClient());
            existingReward.setRewardType(updatedReward.getRewardType());
            return rewardRepository.save(existingReward);
        }).orElse(null); // Handle the case where the reward doesn't exist
    }

    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
}
