package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.UsersRequestDto;
import com.project.tracker.dto.responseDto.UsersResponseDto;
import com.project.tracker.services.serviceInterfaces.UsersService;
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
    private final UsersService usersService;

    public DeveloperController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    @CacheEvict(value = {"developers","top5Developers"}, allEntries = true)
    public ResponseEntity<UsersResponseDto> createDeveloper(@Valid @RequestBody UsersRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.addDeveloper(request));
    }

    @DeleteMapping("delete/{id}")
    @CacheEvict(value = {"developers","top5Developers"}, allEntries = true)
    public ResponseEntity<String> deleteDeveloper(@PathVariable int id){
        usersService.deleteDeveloper(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Users Deleted Successfully");
    }

    @PutMapping("update/{id}")
    @CacheEvict(value = {"developers","top5Developers"}, allEntries = true)
    public ResponseEntity<UsersResponseDto> updateDeveloper(@PathVariable int id, @Valid @RequestBody UsersRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.updateDeveloper(id,request));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "developers", key = "#id")
    public ResponseEntity<UsersResponseDto> getDeveloper(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.getDeveloperById(id));
    }

    @GetMapping
    @Cacheable(value = "developers", key = "T(java.util.Objects).hash(#pageNumber, #sortBy)")
    public ResponseEntity<Page<UsersResponseDto>> getAllDevelopers(
            @RequestParam(required = false, defaultValue = "SORT_BY_ID") DeveloperSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.getAllDevelopers(pageNumber,sortBy.getField()));
    }

    @Cacheable(value = "top5Developers")
    @GetMapping("/top5Developers")
    public ResponseEntity<Page<UsersResponseDto>> getTopDevelopers(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.getTopDevelopers());
    }
}
