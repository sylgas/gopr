package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ucash on 22.09.14.
 */
@Controller
@RequestMapping("/setActionGroups")
public class ActionGroupController {
    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "setActionGroups";
    }
}