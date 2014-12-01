package com.springapp.mvc.api;

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

    @RequestMapping(value="action-list", method = RequestMethod.GET)
    public String actionList() {
        return "action-list";
    }

    @RequestMapping(value="action-groups", method = RequestMethod.GET)
    public String actionGroups() {
        return "action-groups";
    }

    @RequestMapping(value="action", method = RequestMethod.GET)
    public String action() {
        return "action";
    }

    @RequestMapping(value="action-create", method = RequestMethod.GET)
    public String createAction() {
        return "action-create"; }

    @RequestMapping(value="group-user", method = RequestMethod.GET)
    public String editUserInGroup() {
        return "group-user-dialog"; }
}