package org.basak.educore.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.CourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, UUID> {
    boolean existsByCourseAndClassroom(Course course, Classroom classroom);
    @Query("SELECT DISTINCT ca.course FROM CourseAssignment ca WHERE ca.classroom IN :classrooms")
    List<Course> findDistinctCoursesByClassrooms(@Param("classrooms") List<Classroom> classrooms);
    @Query("SELECT ca.course FROM CourseAssignment ca WHERE ca.classroom = :classroom")
    List<Course> findCoursesByClassroom(@Param("classroom") Classroom classroom);


}