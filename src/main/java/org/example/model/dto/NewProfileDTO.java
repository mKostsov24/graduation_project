package org.example.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewProfileDTO {
    public MultipartFile photo;
    public boolean removePhoto;
    public String name;
    public String email;
    public String password;
}
