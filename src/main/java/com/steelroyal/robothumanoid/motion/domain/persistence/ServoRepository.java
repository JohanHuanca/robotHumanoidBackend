package com.steelroyal.robothumanoid.motion.domain.persistence;

import com.steelroyal.robothumanoid.motion.domain.model.Servo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServoRepository extends JpaRepository<Servo, Integer> {
}
