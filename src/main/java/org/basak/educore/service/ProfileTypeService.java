package org.basak.educore.service;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.model.entity.ProfileType;
import org.basak.educore.repository.ProfileTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileTypeService {
    private final ProfileTypeRepository profileTypeRepository;

    public ProfileType findById(Integer id) {
        return profileTypeRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.PROFILE_TYPE_NOT_FOUND));
    }
}