package com.steelroyal.robothumanoid.motion.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServoResource {
    Integer id;
    Integer angle;
}
