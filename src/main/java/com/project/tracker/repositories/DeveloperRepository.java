package com.project.tracker.repositories;


import com.project.tracker.models.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Page<Developer> findAll(Pageable pageable);

    //Top 5 developers with most tasks
    @Query("SELECT d FROM Developer d LEFT JOIN d.tasks t GROUP BY d ORDER BY COUNT(t) DESC")
    Page<Developer> findTop5ByOrderByTasksCountDesc(Pageable pageable);
}
