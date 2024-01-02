package com.steelroyal.robothumanoid.motion.domain.service;

import com.steelroyal.robothumanoid.motion.domain.model.Button;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ButtonService {
    Page<Button> getAll(Pageable pageable);
    Button create(Button button);
    Button update(Button button);
    Boolean delete(Integer buttonId);
}
