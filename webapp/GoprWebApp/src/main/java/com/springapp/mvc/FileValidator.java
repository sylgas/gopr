package com.springapp.mvc;

import org.apache.commons.io.FilenameUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Ucash on 09.06.14.
 */
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return false;
    }

    @Override
    public void validate(Object uploadedFile, Errors errors) {

        FileUpload file = (FileUpload) uploadedFile;
        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.selectFile",
                    "Wybierz plik!");
            return;
        }
        if (!file.getFile().getOriginalFilename().endsWith(".lyr"))
            errors.rejectValue("file", "uploadForm.selectFile",
                    "Niepoprawny format pliku!");
    }
}