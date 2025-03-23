package org.example.medilinkspring.hospital.service;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.exception.ErrorCode;
import org.example.medilinkspring.exception.customexception.HospitalException;
import org.example.medilinkspring.hospital.dto.HospitalResponse;
import org.example.medilinkspring.hospital.entity.Hospital;
import org.example.medilinkspring.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
@Slf4j

@Service
public class HospitalService {
    private final RestTemplate restTemplate;
    private final String apiKey;

    @Autowired
    private HospitalRepository hospitalRepository;

    public HospitalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("HOSPITEAL_KEY");  // 환경 변수 한 번만 로드
    }

    // 주변 병원 검색 (GET 요청)
    public String getHospitalList(float x, float y) throws URISyntaxException {


        String url = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";

        URI uri = new URI(url +
                "?ServiceKey=" + apiKey +
                "&numOfRows=15" +
                "&xPos=" + x +
                "&yPos=" + y +
                "&radius=1500");

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
//
//        Gson gson = new Gson();
//        HospitalResponse rs = gson.fromJson(response.getBody(), HospitalResponse.class);
//
//
//        if (rs != null ) {
//            // 첫 번째 병원 정보 출력
//            HospitalResponse.Response.Body.Items.Item item = rs.getResponse().getBody().getItems().getItem().get(0);
//            System.out.println("병원 이름: " + item.getYadmNm());
//            System.out.println("주소: " + item.getAddr());
//            System.out.println("전화번호: " + item.getTelno());
//        } else {
//            System.out.println("병원 정보가 없습니다.");
//        }
        return response.getBody();
    }
    public String saveData(int x) throws URISyntaxException {


        String url = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";

        URI uri = new URI(url +
                "?ServiceKey=" + apiKey +
                "&numOfRows=5000"+
                "&pageNo="+x
        );

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
//
        Gson gson = new Gson();
        HospitalResponse rs = gson.fromJson(response.getBody(), HospitalResponse.class);


        if (rs != null ) {
            // 첫 번째 병원 정보 출력
            HospitalResponse.Response.Body.Items.Item item = rs.getResponse().getBody().getItems().getItem().get(0);
            System.out.println("병원 이름: " + item.getYadmNm());
            System.out.println("주소: " + item.getAddr());
            System.out.println("전화번호: " + item.getTelno());
        } else {
            System.out.println("병원 정보가 없습니다.");
        }

        for (int i=0;i<rs.getResponse().getBody().getItems().getItem().size();i++){
            HospitalResponse.Response.Body.Items.Item item = rs.getResponse().getBody().getItems().getItem().get(i);

            Hospital hospital =new Hospital(item.getAddr(),item.getClCd(),item.getClCdNm(),item.getDistance(),item.getTelno(),item.getYadmNm(),item.getXPos(),item.getYPos());
            hospitalRepository.save(hospital);
        }
        return "저장성공";
    }

    public List<Hospital> getHospitals(double x,double y) throws HospitalException {
        double minLat=y-0.0053;
        double maxLat=y+0.0053;

        double minLon=x-0.0053;
        double maxLon=x+0.0053;
        if (122> x || x>133 || 30>y || y>39){
            throw new HospitalException(ErrorCode.COORDINATE_NOT_KOREA);
        }

        try {
            List<Hospital> hospitals=hospitalRepository.findHospitalsInRange(minLat,maxLat,minLon,maxLon);
        }catch (Exception e){
            throw new HospitalException(ErrorCode.HOSPITAL_NOT_FOUND,e);
        }

        return hospitalRepository.findHospitalsInRange(minLat,maxLat,minLon,maxLon);

    }


}