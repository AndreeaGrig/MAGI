package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class PDFStoreServiceImpl implements PDFStoreService{

    private static String UPLOADED_FOLDER = "C://Users//andreea//Documents//OnlineLibrary//src//main//resources//static//pdfs//";

    BookRepository bookRepository;

    public PDFStoreServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void savePdfFile(Long bookId, MultipartFile file) {
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + bookId + ".pdf");
            Files.write(path, bytes);
            Book book = bookRepository.findById(bookId).get();
            log.info("Saving pdf file for book '"+book.getTitle()+"'");
            book.setUrlPdf(bookId + ".pdf");
            bookRepository.save(book);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
