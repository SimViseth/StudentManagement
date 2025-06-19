package com.example.studentmanagement.repository.mysql;

import com.example.studentmanagement.model.entity.mysql.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
