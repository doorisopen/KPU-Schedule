package kpuapi.kpulecture.api;

import kpuapi.kpulecture.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsageApiController {

    private final UsageService usageService;

    @GetMapping("/api/v1/usage")
    public Result findAll() {
        return new Result(HttpStatus.OK, usageService.findAll());
    }
}
