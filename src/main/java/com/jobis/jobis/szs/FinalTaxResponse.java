package com.jobis.jobis.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalTaxResponse {
    @Schema(example = "1,000,000", title = "결정세액")
    String 결정세액;
}
