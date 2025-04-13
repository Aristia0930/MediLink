package org.example.medilinkspring.doctor.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "Doctors") // MongoDB 컬렉션 이름 지정
public class Doctor {
    @Id
    private String id; // MongoDB의 ObjectId
    private String hospitalId;
    private String name;
    private String department;
    //진료요일
    private WorkingDays days;
    private WorkingTime times;


    @Data
    public static class WorkingDays {
        private boolean Mon;
        private boolean Tue;
        private boolean Wed;
        private boolean Thu;
        private boolean Fri;
        private boolean Sat;
        private boolean Sun;

    }

    @Data
    public static class WorkingTime {
        private DailyTime Mon;
        private DailyTime Tue;
        private DailyTime Wed;
        private DailyTime Thu;
        private DailyTime Fri;
        private DailyTime Sat;
        private DailyTime Sun;

        @Data
        public static class DailyTime {
            private String start; // 예: "09:00"
            private String end;   // 예: "17:00"
        }

    }
}
