package com.springapp.mvc.api;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ucash on 13.07.14.
 */

@Controller
@RequestMapping("/createAction")
public class CreateActionController {
    private static final Logger logger = Logger.getLogger(CreateActionController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "createAction";
    }
}