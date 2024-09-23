package com.almousleck.mapper;

import com.almousleck.domain.Book;
import com.almousleck.domain.Feedback;
import com.almousleck.request.FeedBackRequest;
import com.almousleck.response.FeedBackResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedBackMapper {

    public Feedback toFeedback(FeedBackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .shareable(false)
                        .archived(false)
                        .build())
                .build();
    }

    public FeedBackResponse toFeedbackResponse(Feedback feedback, Long id) {
        return FeedBackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreateBy(), id))
                .build();
    }
}
