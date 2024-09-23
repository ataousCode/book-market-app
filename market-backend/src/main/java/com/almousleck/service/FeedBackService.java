package com.almousleck.service;

import com.almousleck.domain.Book;
import com.almousleck.domain.Feedback;
import com.almousleck.domain.User;
import com.almousleck.exception.OperationNotPermittedException;
import com.almousleck.exception.ResourceNotFoundException;
import com.almousleck.mapper.FeedBackMapper;
import com.almousleck.repository.BookRepository;
import com.almousleck.repository.FeedBackRepository;
import com.almousleck.request.FeedBackRequest;
import com.almousleck.response.FeedBackResponse;
import com.almousleck.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final BookRepository bookRepository;
    private final FeedBackMapper feedBackMapper;

    public Long save(@Valid FeedBackRequest request, Authentication authentication) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with the given id: " + request.bookId()));
        if (book.isArchived() || !book.isShareable())
            throw new OperationNotPermittedException("You cannot give a feedback for and archived or not shareable book");
        if (Objects.equals(book.getCreateBy(), authentication.getName()))
            throw new OperationNotPermittedException("You cannot give a feedback to your own book.");
        Feedback feedback = feedBackMapper.toFeedback(request);
        return feedBackRepository.save(feedback).getId();
    }

    @Transactional
    public PageResponse<FeedBackResponse> findAllFeedbacksByBook(Long bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedBackRepository.findAllByBookId(bookId, pageable);
        List<FeedBackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedBackMapper.toFeedbackResponse(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );

    }



















}
