package com.steelroyal.robothumanoid.motion.resource;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServoResource {
    @NotNull
    Integer id;

    @NotNull
    @Min(0)
    @Max(180)
    Integer angle;
}
