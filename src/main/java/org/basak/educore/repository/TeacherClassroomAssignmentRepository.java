package org.basak.educore.repository;
import io.lettuce.core.dynamic.annotation.Param;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.TeacherClassroomAssignment;
import org.basak.educore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TeacherClassroomAssignmentRepository extends JpaRepository<TeacherClassroomAssignment, UUID> {
    boolean existsByTeacherAndClassroom(User teacher, Classroom classroom);

    @Query("SELECT a.classroom FROM TeacherClassroomAssignment a WHERE a.teacher = :teacher")
    List<Classroom> findClassroomsByTeacher(@Param("teacher") User teacher);
}