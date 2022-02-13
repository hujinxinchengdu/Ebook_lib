package com.project.hjxwiki.controller;

import com.project.hjxwiki.domain.Demo;
import com.project.hjxwiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinxin Hu
 * @version 1.0
 */

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;


    @GetMapping("/list")
    public List<Demo> list() {
        return demoService.list();
    }
}
