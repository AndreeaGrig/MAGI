package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.User;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.PDFStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PDFStoreController {

    BookService bookService;
    PDFStoreService pdfStoreService;

    public PDFStoreController(PDFStoreService pdfStoreService, BookService bookService) {
        this.pdfStoreService = pdfStoreService;
        this.bookService = bookService;
    }


    @GetMapping("book/{id}/uploadPDF")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.findBookById(Long.valueOf(id)));
        return "pdfForm"; }


    @PostMapping("/book/{id}/uploadPDF") // //new annotation since 4.3
    public String singleFileUpload(@ModelAttribute("book") Book book,
                                   BindingResult result,
                                   @PathVariable String id,
                                   @RequestParam("pdf") MultipartFile file) {
        if(file.getSize()==0)
            result.addError(new ObjectError("book","Please load your book in pdf format!"));
        if (result.hasErrors())
            return "pdfForm" ;
        pdfStoreService.savePdfFile(Long.valueOf(id), file);
        return "redirect:/book/details/" + id;
    }


}
