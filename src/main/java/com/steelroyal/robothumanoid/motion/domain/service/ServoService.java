package com.steelroyal.robothumanoid.motion.domain.service;

import com.steelroyal.robothumanoid.motion.domain.model.Servo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServoService {
    Page<Servo> getAll(Pageable pageable);
    Servo create(Servo servo);
    Servo update(Servo servo);
    Boolean delete(Integer servoId);
}
