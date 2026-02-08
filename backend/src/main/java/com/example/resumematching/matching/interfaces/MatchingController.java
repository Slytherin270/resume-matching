package com.example.resumematching.matching.interfaces;

import com.example.resumematching.matching.application.MatchCommand;
import com.example.resumematching.matching.application.MatchResult;
import com.example.resumematching.matching.application.MatchingService;
import com.example.resumematching.matching.interfaces.dto.MatchingRequest;
import com.example.resumematching.matching.interfaces.dto.MatchingResponse;
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
        MatchResult result = matchingService.match(new MatchCommand(request.resumeId(), request.jobDescription()));
        return new MatchingResponse(result.score(), result.matchedKeywords(), result.totalKeywords());
    }
}
