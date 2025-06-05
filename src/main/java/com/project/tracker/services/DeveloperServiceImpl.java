package com.project.tracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.responseDto.DeveloperResponseDto;
import com.project.tracker.exceptions.customExceptions.DeveloperNotFoundException;
import com.project.tracker.models.Developer;
import com.project.tracker.repositories.DeveloperRepository;
import com.project.tracker.services.serviceInterfaces.DeveloperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final ObjectMapper objectMapper;
    private final DeveloperRepository developerRepository;

    public DeveloperServiceImpl(DeveloperRepository developerRepository,ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.developerRepository = developerRepository;
    }

    @Override
    public DeveloperResponseDto addDeveloper(DeveloperRequestDto requestDto) {
        Developer developer = Developer.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .skills(requestDto.skills())
                .build();
        return objectMapper
                .convertValue(developerRepository.save(developer),
                        DeveloperResponseDto.class);
    }

    @Override
    public void deleteDeveloper(int id) {
        Developer developer = developerRepository
                .findById(id).
                orElseThrow(()->new DeveloperNotFoundException
                        ("Developer with ID: "+id +" not found")); // Throw exception if not found
        developerRepository.delete(developer);
    }

    @Override
    public DeveloperResponseDto updateDeveloper(int id, DeveloperRequestDto requestDto) {
        //fetch developer to be updated
        if(!developerRepository.existsById(id)){
            //Throw exception if not found
            throw  new DeveloperNotFoundException("Developer with ID: "+id+" not found");
        }

        Developer updatedDeveloper = Developer.builder()
                .id(id)
                .name(requestDto.name())
                .email(requestDto.email())
                .skills(requestDto.skills())
                .build();

        return objectMapper
                .convertValue(developerRepository.save(updatedDeveloper),
                        DeveloperResponseDto.class);
    }

    @Override
    public DeveloperResponseDto getDeveloperById(int id) {
        Developer developer = developerRepository
                .findById(id)
                .orElseThrow(()-> new DeveloperNotFoundException
                        ("Developer with ID: "+id+" not found")); // Throw exception if not found
        return objectMapper
                .convertValue(developer,
                        DeveloperResponseDto.class);
    }

    @Override
    public Page<DeveloperResponseDto> getAllDevelopers(int pageNumber,String sortBy) {
        //paginate by
        int paginateBy = 10;

        //sort
        Sort sort = Sort.by(sortBy);

        //Pageable object
        Pageable pageable = PageRequest.of(pageNumber, paginateBy, sort);

        Page<Developer> developers = developerRepository.findAll(pageable);

        return developers
                .map(developer -> objectMapper
                        .convertValue(developer,
                                DeveloperResponseDto.class))
                ;
    }
}
