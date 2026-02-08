package com.example.resumematching.matching;

import com.example.resumematching.matching.dto.MatchingRequest;
import com.example.resumematching.matching.dto.MatchingResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matching")
public class MatchingController {
    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/run")
    public MatchingResponse runMatch(@Valid @RequestBody MatchingRequest request) {
        return matchingService.match(request);
    }
}
