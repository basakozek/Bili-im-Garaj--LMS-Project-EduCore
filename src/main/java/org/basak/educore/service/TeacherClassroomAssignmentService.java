package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.TeacherClassroomAssignmentMapper;
import org.basak.educore.model.dto.request.AssignTeacherRequestDto;
import org.basak.educore.model.dto.response.TeacherClassroomAssignmentResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.TeacherClassroomAssignment;
import org.basak.educore.model.entity.User;
import org.basak.educore.repository.TeacherClassroomAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherClassroomAssignmentService {
    private final UserService userService;
    private final ClassroomService classroomService;
    private final TeacherClassroomAssignmentRepository assignmentRepository;
    private final TeacherClassroomAssignmentMapper mapper;

    @Transactional
    public TeacherClassroomAssignmentResponseDto assignTeacherToClassroom(AssignTeacherRequestDto dto) {
        User teacher = userService.findById(dto.teacherId());
        Classroom classroom = classroomService.findById(dto.classroomId());

        if (!teacher.getProfileType().getId().equals(2)) {
            throw new EduCoreException(ErrorType.USER_IS_NOT_TEACHER);
        }

        boolean alreadyAssigned = assignmentRepository.existsByTeacherAndClassroom(teacher, classroom);

        if (alreadyAssigned) {
            throw new EduCoreException(ErrorType.TEACHER_ALREADY_ASSIGNED);
        }

        TeacherClassroomAssignment assignment = TeacherClassroomAssignment.builder()
                .teacher(teacher)
                .classroom(classroom)
                .build();

        assignmentRepository.save(assignment);
        return mapper.toResponseDto(assignment);
    }
}