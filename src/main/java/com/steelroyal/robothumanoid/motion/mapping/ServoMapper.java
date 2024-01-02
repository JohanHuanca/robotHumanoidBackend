package com.steelroyal.robothumanoid.motion.mapping;

import com.steelroyal.robothumanoid.motion.domain.model.Button;
import com.steelroyal.robothumanoid.motion.domain.model.Servo;
import com.steelroyal.robothumanoid.motion.resource.ButtonResource;
import com.steelroyal.robothumanoid.motion.resource.CreateServoResource;
import com.steelroyal.robothumanoid.motion.resource.ServoResource;
import com.steelroyal.robothumanoid.motion.resource.UpdateServoResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServoMapper {
    public ServoResource toResource(Servo servo){
        return ServoResource.builder()
                .id(servo.getId())
                .angle(servo.getAngle())
                .build();
    }
    public Servo toModel(UpdateServoResource resource){
        return Servo.builder()
                .id(resource.getId())
                .angle(resource.getAngle())
                .build();
    }

    public Servo toModel(CreateServoResource resource){
        return Servo.builder()
                .angle(resource.getAngle())
                .build();
    }
    public Page<ServoResource> toResourcePage(Page<Servo> servosPage) {
        return servosPage.map(this::toResource);
    }

}
