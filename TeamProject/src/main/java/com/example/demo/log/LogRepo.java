package com.example.demo.log;

import com.example.demo.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for log which extends JpaRepository in Swing Framework
 */
@Repository
public interface LogRepo extends JpaRepository<Log, Long> {

}
