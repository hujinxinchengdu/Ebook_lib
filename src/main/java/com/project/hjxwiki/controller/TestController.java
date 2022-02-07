package com.project.hjxwiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jinxin Hu
 * @version 1.0
 */

@RestController
public class TestController {

    @Value("${test.hello:TEXT}")
    private String testHello;


    /**
     * GET, POST, PUT, DELETE
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!" + testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost (String name) {
        return "Hello World! Post," + name;
    }
}
