package kpuapi.kpulecture.api.dto;

import kpuapi.kpulecture.domain.Usage;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class UsageUseRequestDto {

    public Usage toEntity() {
        return Usage.builder()
                .date(LocalDate.now())
                .used(1)
                .build();
    }
}
