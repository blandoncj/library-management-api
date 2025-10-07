package com.library.library_management_api.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_api.persistence.entity.LoanEntity;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    
    List<LoanEntity> findByUserId(Long userId);

}
