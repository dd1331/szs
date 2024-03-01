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
public class ScrapRequest {
    @Schema(example = "동탁")
    private String name;
    @Schema(example = "921108-1582816")
    private String regNo;

}