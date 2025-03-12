package org.example.medilinkspring.hospital.dto;

import lombok.Data;
import java.util.List;

@Data
public class HospitalResponse {
    private Response response;
    @Data
    public static class Response{
        private Header header;
        private Body body;
        @Data
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Data
        public static class Body {
            private Items items;

            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    private String addr;
                    private int clCd;
                    private String clCdNm;
                    private String distance;
                    private String telno;
                    private String yadmNm;
                    private double XPos;  // XPos는 double이 맞음
                    private double YPos;  // YPos는 String이 맞음
                }
            }
        }
    }



}

