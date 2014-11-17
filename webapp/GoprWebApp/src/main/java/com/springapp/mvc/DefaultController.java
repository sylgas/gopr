package com.springapp.mvc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DefaultController {
    private static final Logger logger = Logger.getLogger(DefaultController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "default";
    }
}