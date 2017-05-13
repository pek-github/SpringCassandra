package pek.hospital.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pek.hospital.domain.DoctorByName;
import pek.hospital.domain.DoctorWithPhotoByName;
import pek.hospital.error.AppError;
import pek.hospital.error.ResourceNotFoundException;
import pek.hospital.repository.DoctorByNameRepository;
import pek.hospital.repository.DoctorWithPhotoByNameRepository;


@Service
public class DoctorService {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorByNameRepository doctorByNameRepository;

    @Autowired
    private DoctorWithPhotoByNameRepository doctorWithPhotoByNameRepository;


    public List<DoctorByName> findDoctor(String lastName, String firstName) throws ResourceNotFoundException {

        List<DoctorByName> doctors = doctorByNameRepository.findByLastNameAndFirstName(lastName, firstName);

        if (doctors.isEmpty()) {
            handleNoDoctorsFound(lastName, firstName);
        }

        return doctors;
    }

    public List<DoctorWithPhotoByName> findDoctorWithPhoto(String lastName, String firstName) throws ResourceNotFoundException {

        List<DoctorWithPhotoByName> doctors
            = doctorWithPhotoByNameRepository.findByLastNameAndFirstName(lastName, firstName);

        if (doctors.isEmpty()) {
            handleNoDoctorsFound(lastName, firstName);
        }

        return doctors;
    }


    private void handleNoDoctorsFound(String lastName, String firstName) throws ResourceNotFoundException {
        String message = String.format("No doctors were found with last name '%s' and first name '%s'", lastName, firstName);
        LOG.debug(message);
        throw new ResourceNotFoundException(message, AppError.REQUESTED_RESOURCE_DOES_NOT_EXIST);
    }

}
