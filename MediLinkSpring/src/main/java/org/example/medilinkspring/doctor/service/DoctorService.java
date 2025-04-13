package org.example.medilinkspring.doctor.service;

import org.example.medilinkspring.doctor.dto.DoctorRequest;
import org.example.medilinkspring.doctor.entity.Doctor;
import org.example.medilinkspring.doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;


    public void joinDoctor(DoctorRequest request){
        Doctor doctor = convertToEntity(request);
        repository.save(doctor);
//        try{
//
//        }catch (Exception e){
//            후에 오류 처리
//        }

    }


    public List<Doctor> getDoctor(String id){
       return repository.findDoctorByHospitalId(id);

//        try{
//
//        }catch (Exception e){
//            후에 오류 처리
//        }

    }


    public Doctor convertToEntity(DoctorRequest dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId()); // 신규 등록인 경우 null이 될 수 있음
        doctor.setHospitalId(dto.getHospitalId());
        doctor.setName(dto.getName());
        doctor.setDepartment(dto.getDepartment());

        // WorkingDays 설정
        if (dto.getDays() != null) {
            Doctor.WorkingDays days = new Doctor.WorkingDays();
            days.setMon(dto.getDays().isMon());
            days.setTue(dto.getDays().isTue());
            days.setWed(dto.getDays().isWed());
            days.setThu(dto.getDays().isThu());
            days.setFri(dto.getDays().isFri());
            days.setSat(dto.getDays().isSat());
            days.setSun(dto.getDays().isSun());
            doctor.setDays(days);
        }

        // WorkingTime 설정
        if (dto.getTimes() != null) {
            Doctor.WorkingTime times = new Doctor.WorkingTime();

            if (dto.getTimes().getMon() != null) {
                Doctor.WorkingTime.DailyTime monTime = new Doctor.WorkingTime.DailyTime();
                monTime.setStart(dto.getTimes().getMon().getStart());
                monTime.setEnd(dto.getTimes().getMon().getEnd());
                times.setMon(monTime);
            }

            if (dto.getTimes().getTue() != null) {
                Doctor.WorkingTime.DailyTime tueTime = new Doctor.WorkingTime.DailyTime();
                tueTime.setStart(dto.getTimes().getTue().getStart());
                tueTime.setEnd(dto.getTimes().getTue().getEnd());
                times.setTue(tueTime);
            }

            if (dto.getTimes().getWed() != null) {
                Doctor.WorkingTime.DailyTime wedTime = new Doctor.WorkingTime.DailyTime();
                wedTime.setStart(dto.getTimes().getWed().getStart());
                wedTime.setEnd(dto.getTimes().getWed().getEnd());
                times.setWed(wedTime);
            }

            if (dto.getTimes().getThu() != null) {
                Doctor.WorkingTime.DailyTime thuTime = new Doctor.WorkingTime.DailyTime();
                thuTime.setStart(dto.getTimes().getThu().getStart());
                thuTime.setEnd(dto.getTimes().getThu().getEnd());
                times.setThu(thuTime);
            }

            if (dto.getTimes().getFri() != null) {
                Doctor.WorkingTime.DailyTime friTime = new Doctor.WorkingTime.DailyTime();
                friTime.setStart(dto.getTimes().getFri().getStart());
                friTime.setEnd(dto.getTimes().getFri().getEnd());
                times.setFri(friTime);
            }

            if (dto.getTimes().getSat() != null) {
                Doctor.WorkingTime.DailyTime satTime = new Doctor.WorkingTime.DailyTime();
                satTime.setStart(dto.getTimes().getSat().getStart());
                satTime.setEnd(dto.getTimes().getSat().getEnd());
                times.setSat(satTime);
            }

            if (dto.getTimes().getSun() != null) {
                Doctor.WorkingTime.DailyTime sunTime = new Doctor.WorkingTime.DailyTime();
                sunTime.setStart(dto.getTimes().getSun().getStart());
                sunTime.setEnd(dto.getTimes().getSun().getEnd());
                times.setSun(sunTime);
            }

            doctor.setTimes(times);
        }

        return doctor;
    }

}
