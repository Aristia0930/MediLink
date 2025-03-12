package org.example.medilinkspring.hospital.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.hospital.dto.HospitalResponse;
import org.example.medilinkspring.hospital.entity.Hospital;
import org.example.medilinkspring.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/hos")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //api를 통해 내 주변 병원 검색
    @GetMapping("/hospitals")
    public String getHospitals(@RequestParam float x, @RequestParam float y) throws URISyntaxException {


        return hospitalService.getHospitalList(x,y);
    }

    //db를 통해 내 주변 병원 검색
    @GetMapping("/gethospitals")
    public ResponseEntity<List<Hospital>> getHospitals(@RequestParam double x, @RequestParam double y){
        log.debug("getHospitals 접근 x:{} y:{}",x,y);
        List<Hospital> hospitals=hospitalService.getHospitals(x,y);
        log.debug("주변 병원 count {}",hospitals.size());
        return ResponseEntity.ok(hospitals);
    }


    @GetMapping("/save")
    public String getHospitals(@RequestParam int x) throws URISyntaxException {
        for (int i = 13 ; i<16; i++){
            hospitalService.saveData(i);
        }

        return "저장성공";
    }



}
