package kpuapi.kpulecture.api.dto;

import kpuapi.kpulecture.domain.Usage;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UsageResponseDto {
    private LocalDate date;
    private int used;

    public UsageResponseDto(Usage usage) {
        this.date = usage.getDate();
        this.used = usage.getUsed();
    }
}
