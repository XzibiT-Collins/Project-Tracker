package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.responseDto.DeveloperResponseDto;
import com.project.tracker.services.serviceInterfaces.DeveloperService;
import com.project.tracker.sortingEnums.DeveloperSorting;
import jakarta.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("/create")
    @CacheEvict(value = "developers", allEntries = true)
    public ResponseEntity<DeveloperResponseDto> createDeveloper(@Valid @RequestBody DeveloperRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(developerService.addDeveloper(request));
    }

    @DeleteMapping("delete/{id}")
    @CacheEvict(value = "developers", allEntries = true)
    public ResponseEntity<String> deleteDeveloper(@PathVariable int id){
        developerService.deleteDeveloper(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Developer Deleted Successfully");
    }

    @PutMapping("update/{id}")
    @CacheEvict(value = "developers", allEntries = true)
    public ResponseEntity<DeveloperResponseDto> updateDeveloper(@PathVariable int id, @Valid @RequestBody DeveloperRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.updateDeveloper(id,request));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "developers", key = "#id")
    public ResponseEntity<DeveloperResponseDto> getDeveloper(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.getDeveloperById(id));
    }

    @GetMapping
    @Cacheable(value = "developers", key = "T(java.util.Objects).hash(#pageNumber, #sortBy)")
    public ResponseEntity<Page<DeveloperResponseDto>> getAllDevelopers(
            @RequestParam(required = false, defaultValue = "SORT_BY_ID") DeveloperSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.getAllDevelopers(pageNumber,sortBy.getField()));
    }
}
