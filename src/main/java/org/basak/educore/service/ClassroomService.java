package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.ClassroomMapper;
import org.basak.educore.mapper.TeacherMapper;
import org.basak.educore.model.dto.request.CreateClassroomRequestDto;
import org.basak.educore.model.dto.response.ClassroomResponseDto;
import org.basak.educore.model.dto.response.TeacherClassroomResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Organization;
import org.basak.educore.model.entity.User;
import org.basak.educore.repository.ClassroomRepository;
import org.basak.educore.repository.TeacherClassroomAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final OrganizationService organizationService;
    private final ClassroomMapper classroomMapper;

    public Classroom findById(UUID id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.CLASSROOM_NOT_FOUND));
    }

    @Transactional
    public ClassroomResponseDto createClassroom(CreateClassroomRequestDto dto) {
        Organization organization = organizationService.findById(dto.organizationId());

        if (classroomRepository.existsByNameIgnoreCaseAndOrganization(dto.name(), organization)) {
            throw new EduCoreException(ErrorType.CLASSROOM_ALREADY_EXISTS);
        }

        Classroom classroom = Classroom.builder()
                .name(dto.name())
                .organization(organization)
                .build();

        classroom = classroomRepository.save(classroom);
        return classroomMapper.toResponseDto(classroom);
    }


}