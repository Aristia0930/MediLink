package org.example.medilinkspring.hospital.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document(collection = "Hospital") // MongoDB 컬렉션 이름 지정
public class Hospital {

    @Id
    private String id; // MongoDB의 ObjectId}
    private String addr;
    private int clCd;
    private String clCdNm;
    private String distance;
    private String telno;
    private String yadmNm;
    private double XPos;  // XPos는 double이 맞음
    private double YPos;

    public Hospital(String addr, int clCd, String clCdNm, String distance, String telno, String yadmNm, double XPos, double YPos) {
        this.addr = addr;
        this.clCd = clCd;
        this.clCdNm = clCdNm;
        this.distance = distance;
        this.telno = telno;
        this.yadmNm = yadmNm;
        this.XPos = XPos;
        this.YPos = YPos;
    }

    public Hospital(String addr, int clCd, String distance, String telno, String yadmNm, double xPos, double yPos) {
        this.addr = addr;
        this.clCd = clCd;
        this.clCdNm = clCdNm;
        this.distance = distance;
        this.telno = telno;
        this.yadmNm = yadmNm;
        this.XPos = xPos;
        this.YPos = yPos;
    }
}

