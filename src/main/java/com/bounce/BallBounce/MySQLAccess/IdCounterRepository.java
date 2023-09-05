package com.bounce.BallBounce.MySQLAccess;

import com.bounce.BallBounce.MySQLAccess.template.IdCounter;
import com.bounce.BallBounce.MySQLAccess.template.ParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdCounterRepository extends JpaRepository<IdCounter, Long> {
}
