package apbdoo.onlineLib.services;
import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private static String UPLOADED_FOLDER = "C://Users//andreea//Documents//OnlineLibrary//src//main//resources//static//images//covers//";

    BookRepository bookRepository;

    public ImageServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long bookId, MultipartFile file) {
       /* try {
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
        }*/

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + bookId + ".jpg");
            Files.write(path, bytes);
            Book book = bookRepository.findById(bookId).get();
            log.info("Saving cover image for book '"+book.getTitle()+"'");
            book.setCover(bookId + ".jpg");
            bookRepository.save(book);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
