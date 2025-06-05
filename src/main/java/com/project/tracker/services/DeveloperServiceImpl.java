package com.project.tracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.responseDto.DeveloperResponseDto;
import com.project.tracker.exceptions.customExceptions.DeveloperNotFoundException;
import com.project.tracker.models.AuditLog;
import com.project.tracker.models.Developer;
import com.project.tracker.repositories.DeveloperRepository;
import com.project.tracker.services.serviceInterfaces.AuditLogService;
import com.project.tracker.services.serviceInterfaces.DeveloperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final ObjectMapper objectMapper;
    private final DeveloperRepository developerRepository;
    private final AuditLogService auditLogService;

    public DeveloperServiceImpl(DeveloperRepository developerRepository,
                                ObjectMapper objectMapper,
                                AuditLogService auditLogService) {
        this.objectMapper = objectMapper;
        this.developerRepository = developerRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public DeveloperResponseDto addDeveloper(DeveloperRequestDto requestDto) {
        Developer developer = Developer.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .skills(requestDto.skills())
                .build();

        Developer savedDeveloper = developerRepository.save(developer);
        logAudit("Create Developer", String.valueOf(savedDeveloper.getId()), savedDeveloper.getName(), savedDeveloper);

        return objectMapper.convertValue(savedDeveloper, DeveloperResponseDto.class);
    }

    @Override
    public void deleteDeveloper(int id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer with ID: " + id + " not found"));

        developerRepository.delete(developer);
        logAudit("Delete Developer", String.valueOf(developer.getId()), developer.getName(), developer);
    }

    @Override
    public DeveloperResponseDto updateDeveloper(int id, DeveloperRequestDto requestDto) {
        if (!developerRepository.existsById(id)) {
            throw new DeveloperNotFoundException("Developer with ID: " + id + " not found");
        }

        Developer updatedDeveloper = Developer.builder()
                .id(id)
                .name(requestDto.name())
                .email(requestDto.email())
                .skills(requestDto.skills())
                .build();

        Developer savedDeveloper = developerRepository.save(updatedDeveloper);
        logAudit("Update Developer", String.valueOf(savedDeveloper.getId()), savedDeveloper.getName(), savedDeveloper);

        return objectMapper.convertValue(savedDeveloper, DeveloperResponseDto.class);
    }

    @Override
    public DeveloperResponseDto getDeveloperById(int id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer with ID: " + id + " not found"));

        logAudit("Get Developer By ID", String.valueOf(developer.getId()), developer.getName(), developer);
        return objectMapper.convertValue(developer, DeveloperResponseDto.class);
    }

    @Override
    public Page<DeveloperResponseDto> getAllDevelopers(int pageNumber, String sortBy) {
        int paginateBy = 10;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNumber, paginateBy, sort);

        Page<Developer> developers = developerRepository.findAll(pageable);

        logAudit("Get all Developers" + paginateBy, "PAGE_"+pageNumber, "None", "Developer");
        return developers.map(developer -> objectMapper.convertValue(developer, DeveloperResponseDto.class));
    }

    private void logAudit(String actionType, String entityId, String actorName, Object entity) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            payload = "Could not serialize payload: " + e.getMessage();
        }

        auditLogService.addAuditLog(AuditLog.builder()
                .actionType(actionType)
                .entityId(String.valueOf(entityId))
                .actorName(actorName)
                .payload(payload)
                .timestamp(Date.valueOf(LocalDate.now()))
                .build());
    }
}
