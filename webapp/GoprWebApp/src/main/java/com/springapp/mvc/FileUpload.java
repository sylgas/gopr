package com.springapp.mvc;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Ucash on 08.06.14.
 */
public class FileUpload {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
