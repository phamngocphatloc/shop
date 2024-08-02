package group6.ecommerce.controller;
import group6.ecommerce.configuration.WebConfig;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.fileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<HttpResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(), "file not empty",null), HttpStatus.BAD_REQUEST);
        }

        try {
            // Tạo đường dẫn đầy đủ
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + uniqueFilename);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // Lưu file vào hệ thống
            Files.write(path, file.getBytes());

            // Trả về link file
            String fileDownloadUri = WebConfig.urlBackend+ "/api/file/uploads/" + uniqueFilename;
            return new ResponseEntity<>(new HttpResponse(HttpStatus.OK.value(), "success",new fileResponse(fileDownloadUri)), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Could not upload the file",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) {
        try {
            Path path = Paths.get(uploadDir + filename);
            byte[] fileContent = Files.readAllBytes(path);

            // Xác định loại MIME của file ảnh
            String mimeType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
