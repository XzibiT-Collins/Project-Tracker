package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.responseDto.DeveloperResponseDto;
import com.project.tracker.services.serviceInterfaces.DeveloperService;
import jakarta.validation.Valid;
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
    protected ResponseEntity<DeveloperResponseDto> createDeveloper(@Valid @RequestBody DeveloperRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(developerService.addDeveloper(request));
    }

    @DeleteMapping("delete/{id}")
    protected ResponseEntity<String> deleteDeveloper(@PathVariable int id){
        developerService.deleteDeveloper(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Developer Deleted Successfully");
    }

    @PutMapping("update/{id}")
    protected ResponseEntity<DeveloperResponseDto> updateDeveloper(@PathVariable int id, @Valid @RequestBody DeveloperRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.updateDeveloper(id,request));
    }

    @GetMapping("/{id}")
    protected ResponseEntity<DeveloperResponseDto> getDeveloper(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.getDeveloperById(id));
    }

    @GetMapping
    protected ResponseEntity<Iterable<DeveloperResponseDto>> getAllDevelopers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(developerService.getAllDevelopers());
    }
}
