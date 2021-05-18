package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.example.model.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class ImageService {
    private String result;
    private String path;

    private static String getRandomPath() {
        StringBuilder sb = new StringBuilder();
        sb.append("/upload/");
        for (int iteration = 0; iteration < 3; iteration++) {
            for (int ch = 0; ch < 2; ch++) {
                sb.append((char) (new Random().nextInt('z' - 'a') + 'a'));
            }
            sb.append("/");
        }
        return sb.toString();
    }

    public String getPath() {
        return path;
    }

    public String getResult() {
        return result;
    }

    public ResponseEntity<?> store(MultipartFile file, HttpServletRequest request) {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Map<String, Object> errors = validateFile(file, filename);

        if (errors.size() > 0) {
            return ResponseEntity.badRequest().body(new ErrorDTO(false, errors));
        }
        try {
            String uploadsDir = getRandomPath() + file.getOriginalFilename();
            String realPath = request.getServletContext().getRealPath(uploadsDir);

            byte[] inputStream = file.getBytes();

            File photo = new File(realPath);
            FileUtils.writeByteArrayToFile(photo, inputStream);

            result = uploadsDir.replace('\\', '/');
            path = realPath;
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            errors.put("image", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorDTO(false, errors));
        }

    }

    public Map<String, Object> validateFile(MultipartFile file, String filename) {
        Map<String, Object> errorsMap = new HashMap<>();

        if (filename.toLowerCase().matches("(?!.*(?:\\.jpe?g|\\.gif|\\.png)$)")) {
            System.out.println(filename);
            errorsMap.put("image", "Файлы только jpg, png");
            return errorsMap;
        }

        if (file.getSize() > 5242880) {
            errorsMap.put("image", "Слишком большой фаил");
            return errorsMap;
        }

        if (file.isEmpty()) {
            errorsMap.put("image", "Пустой фаил");
            return errorsMap;
        }

        if (filename.contains("..")) {
            errorsMap.put("image", "Некорректное имя");
            return errorsMap;
        }

        if (file.isEmpty()) {
            errorsMap.put("image", "Пустой фаил");
            return errorsMap;
        }
        return errorsMap;
    }


}
