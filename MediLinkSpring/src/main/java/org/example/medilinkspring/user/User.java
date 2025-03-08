package org.example.medilinkspring.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "users") // MongoDB 컬렉션 이름 지정
public class User {

    @Id
    private String id; // MongoDB의 ObjectId
    @Indexed(unique = true)
    private String name;
    private String password;
    private String phone;
    private int age;
    private String address;
    private String role; // "user", "doctor", "admin"
    private Date createdAt;
    private String img; // Firebase 스토리지 URL


    // 기본 생성자
    public User() {
        this.createdAt = new Date(); // 생성 시 자동으로 현재 날짜 설정
    }

    // 생성자
    public User(String name,String password, String phone, int age, String address, String role) {
        this.name = name;
        this.phone = phone;
        this.password=password;
        this.age = age;
        this.address = address;
        this.role = role;
        this.createdAt = new Date(); // 생성 시 자동으로 설정
    }
}
