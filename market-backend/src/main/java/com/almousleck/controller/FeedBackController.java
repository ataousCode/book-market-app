package com.almousleck.controller;

import com.almousleck.request.FeedBackRequest;
import com.almousleck.response.FeedBackResponse;
import com.almousleck.response.PageResponse;
import com.almousleck.service.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedBackController {
    private final FeedBackService feedBackService;

    @PostMapping
    public ResponseEntity<Long> saveFeedBack(@Valid @RequestBody FeedBackRequest request, Authentication authentication) {
        return ResponseEntity.ok(feedBackService.save(request, authentication));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<FeedBackResponse>> findAllFeedBacksByBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(feedBackService.findAllFeedbacksByBook(bookId, page, size, authentication));
    }
}
