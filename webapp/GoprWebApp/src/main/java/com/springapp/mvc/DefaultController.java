package com.springapp.mvc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
@RequestMapping("/")
public class DefaultController {
    private static final Logger logger = Logger.getLogger(DefaultController.class);
    private static final String filePath = "C:\\Users\\Ucash\\Documents\\gopr\\webapp\\GoprWebApp\\layer\\Areas.lyr";

    @Autowired
    FileValidator fileValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "default";
    }

    @RequestMapping("/fileUploadForm")
    public ModelAndView getUploadForm(
            @ModelAttribute("uploadedFile") FileUpload uploadedFile,
            BindingResult result) {
        return new ModelAndView("default");
    }

    @RequestMapping("/fileUpload")
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") FileUpload uploadedFile,
            BindingResult result) {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename();

        if (result.hasErrors()) {
            return new ModelAndView("default");
        }

        try {
            inputStream = file.getInputStream();

            File newFile = new File(filePath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView
                ("action", "layerName", fileName);
    }
}