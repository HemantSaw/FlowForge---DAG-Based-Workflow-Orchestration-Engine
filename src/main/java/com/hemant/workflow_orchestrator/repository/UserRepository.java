package com.hemant.workflow_orchestrator.repository;
import com.hemant.workflow_orchestrator.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    
}
