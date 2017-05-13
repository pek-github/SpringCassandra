package pek.hospital.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import pek.hospital.domain.DoctorWithPhotoByName;


@Repository
public interface DoctorWithPhotoByNameRepository extends CassandraRepository<DoctorWithPhotoByName> {

    List<DoctorWithPhotoByName> findByLastNameAndFirstName(String lastName, String firstName);
}
