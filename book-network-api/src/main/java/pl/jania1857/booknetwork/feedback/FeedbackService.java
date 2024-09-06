package pl.jania1857.booknetwork.feedback;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.jania1857.booknetwork.book.Book;
import pl.jania1857.booknetwork.book.BookRepository;
import pl.jania1857.booknetwork.exception.OperationNotPermittedException;
import pl.jania1857.booknetwork.user.User;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {


    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find book"));
        User user = (User) connectedUser;
        if(!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("You cannot give a feedback - archived/shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give a feedback to your own book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }
}
