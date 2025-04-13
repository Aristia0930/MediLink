package org.example.medilinkspring.doctor.repository;

import org.example.medilinkspring.doctor.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor,String> {


    List<Doctor> findDoctorByHospitalId(String id);

}
