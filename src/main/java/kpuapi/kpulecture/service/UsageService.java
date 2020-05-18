package kpuapi.kpulecture.service;

import kpuapi.kpulecture.api.dto.UsageResponseDto;
import kpuapi.kpulecture.domain.Usage;
import kpuapi.kpulecture.domain.UsageRepository;
import kpuapi.kpulecture.domain.school.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UsageService {

    private final UsageRepository usageRepository;


    @Transactional
    public void use(Usage usage) {
        Usage findTopUsage = usageRepository.findTopByOrderByDateDesc();
        if(!findTopUsage.getDate().isEqual(usage.getDate())){
            usageRepository.save(usage);
        } else {
            findTopUsage.use();
        }
    }

    public List<UsageResponseDto> findAll() {
        return usageRepository.findAll().stream()
                .map(UsageResponseDto::new)
                .collect(Collectors.toList());
    }
}
