package com.project.tracker.repositories;

import com.project.tracker.models.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    Page<AuditLog> getAllAuditLogs(Pageable pageable);
}
