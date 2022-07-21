package com.mck.cloudparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mck.cloudparking.model.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {


    
}
