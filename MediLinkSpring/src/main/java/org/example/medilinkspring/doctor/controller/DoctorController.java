package org.example.medilinkspring.doctor.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.doctor.dto.DoctorRequest;
import org.example.medilinkspring.doctor.entity.Doctor;
import org.example.medilinkspring.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/join")
    private ResponseEntity<String> joinDoctor(@RequestBody DoctorRequest request){

         doctorService.joinDoctor(request);
        return ResponseEntity.ok("의사 데이터 저장 성공");
    }


    //병원 아이디로 의사 조회
    @GetMapping("/getdoctor")
    private ResponseEntity<List<Doctor>> getDoctor(@RequestParam String id){
        List<Doctor> doctors=doctorService.getDoctor(id);
        return ResponseEntity.ok(doctors);
    }



}
