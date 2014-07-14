package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ucash on 13.07.14.
 */

@Controller
@RequestMapping("/action")
public class ActionController {

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "action";
    }
}
