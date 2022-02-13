package com.project.hjxwiki.service;

import com.project.hjxwiki.domain.Test;
import com.project.hjxwiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinxin Hu
 * @version 1.0
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
