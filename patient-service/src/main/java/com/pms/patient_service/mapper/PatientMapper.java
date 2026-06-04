package com.pms.patient_service.mapper;

import com.pms.patient_service.model.Patient;
import com.pms.patient_service.dto.PatientResponseDTO;
import com.pms.patient_service.dto.PatientRequestDTO;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO PatientDTO = new PatientResponseDTO();
        PatientDTO.setId(patient.getId().toString());
        PatientDTO.setName(patient.getName());
        PatientDTO.setEmail(patient.getEmail());
        PatientDTO.setAddress(patient.getAddress());
        PatientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return PatientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegistrationDate(LocalDate.parse(patientRequestDTO.getRegistrationDate()));

        return patient;
    }
}