package com.example.clientMicroservice.service;

import com.example.clientMicroservice.models.RewardType;
import com.example.clientMicroservice.repository.RewardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardTypeService {

    @Autowired
    private RewardTypeRepository rewardTypeRepository;

    public List<RewardType> getAllRewardTypes() {
        return rewardTypeRepository.findAll();
    }

    public Optional<RewardType> getRewardTypeById(Long id) {
        return rewardTypeRepository.findById(id);
    }

    public RewardType saveRewardType(RewardType rewardType) {
        return rewardTypeRepository.save(rewardType);
    }

    public RewardType updateRewardType(Long id, RewardType updatedRewardType) {
        return rewardTypeRepository.findById(id).map(existingRewardType -> {
            existingRewardType.setName(updatedRewardType.getName());
            return rewardTypeRepository.save(existingRewardType);
        }).orElse(null); // Handle the case where the reward type doesn't exist
    }

    public void deleteRewardType(Long id) {
        rewardTypeRepository.deleteById(id);
    }
}