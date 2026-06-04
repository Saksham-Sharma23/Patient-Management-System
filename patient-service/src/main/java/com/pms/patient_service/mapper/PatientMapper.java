package com.pms.patient_service.mapper;

import com.pms.patient_service.model.Patient;
import com.pms.patient_service.dto.PatientResponseDTO;


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
}