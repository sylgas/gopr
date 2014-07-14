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

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "default";
    }
}