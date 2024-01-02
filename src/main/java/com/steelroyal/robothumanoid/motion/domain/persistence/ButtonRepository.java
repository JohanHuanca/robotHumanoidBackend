package com.steelroyal.robothumanoid.motion.domain.persistence;

import com.steelroyal.robothumanoid.motion.domain.model.Button;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButtonRepository extends JpaRepository<Button, Integer> {
}
