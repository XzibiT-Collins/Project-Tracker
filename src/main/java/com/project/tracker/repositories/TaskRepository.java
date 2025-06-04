package com.project.tracker.repositories;

import com.project.tracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Integer, Task> {

}
