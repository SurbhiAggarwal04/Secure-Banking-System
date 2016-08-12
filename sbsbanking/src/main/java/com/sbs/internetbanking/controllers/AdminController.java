package com.sbs.internetbanking.controllers;

import com.sbs.internetbanking.persistence.RequestManager;
import com.sbs.internetbanking.persistence.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserManager userManager;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RequestManager requestManager;

    @RequestMapping(value = "/viewLogFile", method = RequestMethod.GET)
    public ModelAndView viewLogFile() {
        String path = System.getProperty("catalina.base") + "/logs/sbsbanking.log";
        File file = new File(path);


        ModelAndView model = new ModelAndView();
        model.setViewName("forgotpassword");
        return model;
    }

}
