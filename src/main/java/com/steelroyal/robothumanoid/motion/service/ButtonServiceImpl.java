package com.steelroyal.robothumanoid.motion.service;

import com.steelroyal.robothumanoid.motion.domain.model.Button;
import com.steelroyal.robothumanoid.motion.domain.persistence.ButtonRepository;
import com.steelroyal.robothumanoid.motion.domain.service.ButtonService;
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
public class ButtonServiceImpl implements ButtonService {
    private final ButtonRepository buttonRepository;
    private final Validator validator;
    @Override
    public Page<Button> getAll(Pageable pageable) {
        return buttonRepository.findAll(pageable);
    }
    @Override
    public Button create(Button button) {
        Set<ConstraintViolation<Button>> violations = validator.validate(button);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return buttonRepository.save(button);
    }
    @Override
    public Button update(Button button) {
        Set<ConstraintViolation<Button>> violations = validator.validate(button);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return buttonRepository.findById(button.getId())
                .map(buttonToUpdate->{
                    buttonToUpdate.setState(button.getState());
                    return buttonRepository.save(buttonToUpdate);
                }).orElseThrow(() -> new EntityNotFoundException("User with ID " + button.getId() + " not found"));
    }
    @Override
    public Boolean delete(Integer buttonId){
        return buttonRepository.findById(buttonId)
                .map(buttonToDelete -> {
                    buttonRepository.delete(buttonToDelete);
                    return true;
                })
                .orElse(false);
    }
}
