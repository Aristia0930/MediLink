package org.example.medilinkspring.hospital.repository;


import org.example.medilinkspring.hospital.entity.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HospitalRepository extends MongoRepository<Hospital,String> {

    @Query("{'YPos': {$gte: ?0, $lte: ?1}, 'XPos': {$gte: ?2, $lte: ?3}}")
    List<Hospital> findHospitalsInRange(double minLat, double maxLat, double minLon, double maxLon);
}

