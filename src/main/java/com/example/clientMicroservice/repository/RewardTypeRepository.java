package com.example.clientMicroservice.repository;

import com.example.clientMicroservice.models.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardTypeRepository extends JpaRepository<RewardType, Long> {
}