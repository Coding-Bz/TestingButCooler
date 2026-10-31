package ch.tbz.m450.testing.tools.repository;

import ch.tbz.m450.testing.tools.repository.entities.Student;
import ch.tbz.m450.testing.tools.repository.entities.University;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UniversityRepository extends CrudRepository<University, UUID> {
    }
