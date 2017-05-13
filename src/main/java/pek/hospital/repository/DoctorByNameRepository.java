package pek.hospital.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import pek.hospital.domain.DoctorByName;


@Repository
public interface DoctorByNameRepository extends CassandraRepository<DoctorByName> {

    List<DoctorByName> findByLastNameAndFirstName(String lastName, String firstName);

}
