package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;

    private final BookService bookService;

    public ImageController(ImageService imageService, BookService bookService) {
        this.imageService = imageService;
        this.bookService = bookService;

    }

        @GetMapping("book/{id}/image")
        public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.findBookById(Long.valueOf(id)));
        return "imageform"; }

        @PostMapping("book/{id}/image")
        public String handleImagePost(@ModelAttribute("book")Book book, BindingResult bindingResult, @PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/book/" + id +"/uploadPDF"; }



    @GetMapping("book/{id}/cover")
    public void downloadCoverImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        Book book = bookService.findBookById(Long.valueOf(id));
        if (book.getCover() != null) {
            byte[] byteArray = new byte[book.getCover().length];
            int i = 0;
            for (Byte wrappedByte : book.getCover()){
                byteArray[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            try {
                IOUtils.copy(is, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}