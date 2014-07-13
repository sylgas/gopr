package com.springapp.mvc;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Paulina
 * Date: 2014-06-14
 */

@Controller
@RequestMapping("/")
public class JSONController {
    private static final Logger logger = Logger.getLogger(JSONController.class);

    @RequestMapping(value = "layer", method = RequestMethod.GET)
    public @ResponseBody Object getLayer() {
        //doesn't work yet - should be in DefaultController
        /*Process process = null;
        try {
            process = Runtime.getRuntime().exec("python O:\\layer\\arc.py");
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String name = "AreasFormated.json";
        File jsonFile = new File("C:\\Users\\Ucash\\Documents\\gopr\\webapp\\GoprWebApp\\layer\\AreasFormated.json");
        URI uri = jsonFile.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        JSONObject featureCollection = new JSONObject();

        featureCollection.put("geometryType", root.getString("geometryType"));
        featureCollection.put("fields", root.getJSONArray("fields"));

        JSONObject parsed = new JSONObject();
        parsed.put("jsonFS", root);
        parsed.put("jsonLD", featureCollection);

        return parsed.toString();
    }
}
