package org.basak.educore.repository;
import org.basak.educore.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
