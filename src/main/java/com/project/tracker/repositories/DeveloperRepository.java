package com.project.tracker.repositories;


import com.project.tracker.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Integer, Developer> {
}
