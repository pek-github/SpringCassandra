package pek.hospital.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pek.hospital.domain.DoctorByName;
import pek.hospital.domain.DoctorWithPhotoByName;
import pek.hospital.error.AppException;
import pek.hospital.service.DoctorService;


@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @RequestMapping(value = "/doctors/{lastName}/{firstName}",
                    method = RequestMethod.GET,
                    produces = "application/json; charset=UTF-8")
    public List<DoctorWithPhotoByName> findDoctor(
            @PathVariable String lastName,
            @PathVariable String firstName,
            @RequestParam(name = "withPhoto", required = false, defaultValue = "false") String withPhoto) throws AppException {

        boolean requestedPhoto = Boolean.parseBoolean(withPhoto);

        if (requestedPhoto) {
            return doctorService.findDoctorWithPhoto(lastName, firstName);
        } else {
            List<DoctorByName> doctorsByName = doctorService.findDoctor(lastName, firstName);
            return doctorsByName.stream().map(DoctorWithPhotoByName::new).collect(Collectors.toList());
        }
    }

}

