package com.steelroyal.robothumanoid.motion.service;

import com.steelroyal.robothumanoid.motion.domain.model.Servo;
import com.steelroyal.robothumanoid.motion.domain.persistence.ServoRepository;
import com.steelroyal.robothumanoid.motion.domain.service.ServoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServoServiceImpl implements ServoService {
    private final ServoRepository servoRepository;
    private final Validator validator;
    @Override
    public Page<Servo> getAll(Pageable pageable) {
        return servoRepository.findAll(pageable);
    }
    @Override
    public Servo create(Servo servo) {
        Set<ConstraintViolation<Servo>> violations = validator.validate(servo);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return servoRepository.save(servo);
    }
    @Override
    public Servo update(Servo servo) {
        Set<ConstraintViolation<Servo>> violations = validator.validate(servo);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return servoRepository.findById(servo.getId())
                .map(servoToUpdate->{
                    servoToUpdate.setAngle(servo.getAngle());
                    return servoRepository.save(servoToUpdate);
                }).orElseThrow(() -> new EntityNotFoundException("User with ID " + servo.getId() + " not found"));
    }
    @Override
    public Boolean delete(Integer servoId) {
        return servoRepository.findById(servoId)
                .map(servoToDelete -> {
                    servoRepository.delete(servoToDelete);
                    return true;
                })
                .orElse(false);
    }
}
