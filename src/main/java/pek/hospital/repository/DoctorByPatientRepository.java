package pek.hospital.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import pek.hospital.domain.DoctorByPatient;


@Repository
public interface DoctorByPatientRepository extends CassandraRepository<DoctorByPatient> {

    List<DoctorByPatient> findByPatientId(String id);

    List<DoctorByPatient> findByPatientIdIn(List<String> ids);

}
