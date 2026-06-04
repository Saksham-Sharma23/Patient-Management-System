package com.pms.patient_service.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pms.patient_service.dto.PatientResponseDTO;
import com.pms.patient_service.model.Patient;
import com.pms.patient_service.repository.PatientRepository;
import com.pms.patient_service.mapper.PatientMapper;
import com.pms.patient_service.dto.PatientRequestDTO;


import com.pms.patient_service.exception.EmailAlreadyExistsException;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        
        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();

        return patientResponseDTOs;
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
}
