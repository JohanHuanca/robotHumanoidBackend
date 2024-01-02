package com.steelroyal.robothumanoid.motion.resource;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateButtonResource {
    @NotNull
    Integer id;

    @NotNull
    Boolean state;
}
