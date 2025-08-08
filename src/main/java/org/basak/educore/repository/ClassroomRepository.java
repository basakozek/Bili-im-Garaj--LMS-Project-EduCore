package org.basak.educore.repository;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    boolean existsByNameIgnoreCaseAndOrganization(String name, Organization organization);

}
