package org.example.medilinkspring.doctor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.medilinkspring.doctor.entity.Doctor;

@Data
public class DoctorRequest {
    private String id;
    private String hospitalId;
    private String name;
    private String department;
    private WorkingDays days;
    private WorkingTime times;

    @Data
    public static class WorkingDays {
        @JsonProperty("Mon")
        private boolean Mon;
        @JsonProperty("Tue")
        private boolean Tue;
        @JsonProperty("Wed")
        private boolean Wed;
        @JsonProperty("Thu")
        private boolean Thu;
        @JsonProperty("Fri")
        private boolean Fri;
        @JsonProperty("Sat")
        private boolean Sat;
        @JsonProperty("Sun")
        private boolean Sun;
    }

    @Data
    public static class WorkingTime {
        @JsonProperty("Mon")
        private DailyTime Mon;
        @JsonProperty("Tue")
        private DailyTime Tue;
        @JsonProperty("Wed")
        private DailyTime Wed;
        @JsonProperty("Thu")
        private DailyTime Thu;
        @JsonProperty("Fri")
        private DailyTime Fri;
        @JsonProperty("Sat")
        private DailyTime Sat;
        @JsonProperty("Sun")
        private DailyTime Sun;

        @Data
        public static class DailyTime {
            @JsonProperty("start")
            private String start;
            @JsonProperty("end")
            private String end;
        }
    }
}