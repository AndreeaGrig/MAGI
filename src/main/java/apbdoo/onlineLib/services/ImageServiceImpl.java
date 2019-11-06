package apbdoo.onlineLib.services;
import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;


@Service
@Slf4j
public class ImageServiceImpl implements ImageService{
    BookRepository bookRepository;

    public ImageServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long bookId, MultipartFile file) {
        try {
            Book book = bookRepository.findById(bookId).get();
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            book.setCover(byteObjects);
            log.info("Saving cover image for book '"+book.getTitle()+"'");
            bookRepository.save(book); }
        catch (IOException e) {
        }
    }
}
