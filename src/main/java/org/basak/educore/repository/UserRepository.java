package org.basak.educore.repository;
import io.lettuce.core.dynamic.annotation.Param;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    // TeacherClassroomAssignmentRepository.java
    @Query("SELECT a.classroom FROM TeacherClassroomAssignment a WHERE a.teacher = :teacher")
    List<Classroom> findClassroomsByTeacher(@Param("teacher") User teacher);

    // UserRepository.java
    List<User> findByClassroomInAndProfileType_Id(List<Classroom> classrooms, Integer profileTypeId);

    // CourseAssignmentRepository.java
    @Query("SELECT DISTINCT ca.course FROM CourseAssignment ca WHERE ca.classroom IN :classrooms")
    List<Course> findDistinctCoursesByClassrooms(@Param("classrooms") List<Classroom> classrooms);
}