package com.bounce.BallBounce.MySQLAccess;

import com.bounce.BallBounce.MySQLAccess.template.ParametersEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametersRepository extends JpaRepository<ParametersEntity, Long> {
    @Nullable
    ParametersEntity
    findByVelocity1AndVelocity2AndMass1AndMass2(double velocity1, double velocity2, double mass1, double mass2);

    @Nullable
    ParametersEntity
    findByFutureId(Long id);
}
