package org.basak.educore.mapper;

import org.basak.educore.model.dto.response.TeacherClassroomAssignmentResponseDto;
import org.basak.educore.model.entity.TeacherClassroomAssignment;
import org.springframework.stereotype.Component;

@Component
public class TeacherClassroomAssignmentMapper {

    public TeacherClassroomAssignmentResponseDto toResponseDto(TeacherClassroomAssignment assignment) {
        return new TeacherClassroomAssignmentResponseDto(
                assignment.getId(),
                assignment.getTeacher().getId(),
                assignment.getTeacher().getFirstName() + " " + assignment.getTeacher().getLastName(),
                assignment.getClassroom().getId(),
                assignment.getClassroom().getName()
        );
    }
}

