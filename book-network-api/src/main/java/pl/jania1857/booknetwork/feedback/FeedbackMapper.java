package pl.jania1857.booknetwork.feedback;

import org.springframework.stereotype.Service;
import pl.jania1857.booknetwork.book.Book;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(
                        Book.builder()
                                .id(request.bookId())
                                .archived(false)
                                .shareable(false)
                                .build()
                )
                .build();
    }
}
