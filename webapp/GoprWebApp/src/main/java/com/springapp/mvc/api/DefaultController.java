package com.springapp.mvc.api;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DefaultController {

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "default";
    }

    @RequestMapping(value = "action-list", method = RequestMethod.GET)
    public String actionList() {
        return "/action/action-list";
    }

    @RequestMapping(value = "action-groups", method = RequestMethod.GET)
    public String actionGroups() {
        return "/action-create/action-groups";
    }

    @RequestMapping(value = "action", method = RequestMethod.GET)
    public String action() {
        return "/action/action";
    }

    @RequestMapping(value = "action-create", method = RequestMethod.GET)
    public String createAction() { return "/action-create/action-create"; }

    @RequestMapping(value = "group-user-edit-dialog", method = RequestMethod.GET)
    public String editUserInGroup() { return "/action-create/group-user-edit-dialog"; }

    @RequestMapping(value = "area-edit-dialog", method = RequestMethod.GET)
    public String editActionArea() { return "/action-create/area-edit-dialog"; }

    @RequestMapping(value = "user-list", method = RequestMethod.GET)
    public String users() { return "/user/user-list"; }

    @RequestMapping(value = "user-add-dialog", method = RequestMethod.GET)
    public String addUser() { return "/user/user-add-dialog"; }
}