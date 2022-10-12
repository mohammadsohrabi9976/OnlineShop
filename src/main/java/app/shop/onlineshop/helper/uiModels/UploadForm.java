package app.shop.onlineshop.helper.uiModels;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

    private String description;

    private MultipartFile file;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

