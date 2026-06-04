package com.pms.patient_service.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pms.patient_service.dto.PatientResponseDTO;
import com.pms.patient_service.model.Patient;
import com.pms.patient_service.repository.PatientRepository;
import com.pms.patient_service.mapper.PatientMapper;

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
}
