package org.basak.educore.mapper;

import org.basak.educore.model.dto.response.ClassroomResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomMapper {
    public ClassroomResponseDto toResponseDto(Classroom classroom) {
        return new ClassroomResponseDto(
                classroom.getId(),
                classroom.getName(),
                classroom.getOrganization().getName()
        );
    }
}