package com.steelroyal.robothumanoid.motion.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableAllRequest {
    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @NotNull
    @NotBlank
    private String sortBy;

    @NotNull
    @NotBlank
    private String sortDirection;
}
