package com.steelroyal.robothumanoid.motion.mapping;

import com.steelroyal.robothumanoid.motion.domain.model.Button;
import com.steelroyal.robothumanoid.motion.resource.ButtonResource;
import com.steelroyal.robothumanoid.motion.resource.CreateButtonResource;
import com.steelroyal.robothumanoid.motion.resource.UpdateButtonResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ButtonMapper {
    public ButtonResource toResource(Button button){
        return ButtonResource.builder()
                .id(button.getId())
                .state(button.getState())
                .build();
    }
    public Button toModel(UpdateButtonResource resource){
        return Button.builder()
                .id(resource.getId())
                .state(resource.getState())
                .build();
    }

    public Button toModel(CreateButtonResource resource){
        return Button.builder()
                .state(resource.getState())
                .build();
    }

    public Page<ButtonResource> toResourcePage(Page<Button> buttonsPage) {
        return buttonsPage.map(this::toResource);
    }
}
