package com.project.hjxwiki.service;

import com.project.hjxwiki.domain.Demo;
import com.project.hjxwiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinxin Hu
 * @version 1.0
 */
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
