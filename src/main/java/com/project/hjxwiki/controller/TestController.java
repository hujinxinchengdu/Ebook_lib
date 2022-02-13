package com.project.hjxwiki.controller;

import com.project.hjxwiki.domain.Test;
import com.project.hjxwiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinxin Hu
 * @version 1.0
 */

@RestController
public class TestController {

    @Value("${test.hello:TEXT}")
    private String testHello;

    @Resource
    private TestService testService;


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

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}
