package apbdoo.onlineLib.services;

import org.springframework.web.multipart.MultipartFile;

public interface PDFStoreService {
    void savePdfFile(Long bookId, MultipartFile file);
}
