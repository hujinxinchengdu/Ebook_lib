package com.project.hjxwiki.controller;

import com.project.hjxwiki.req.EbookReq;
import com.project.hjxwiki.resp.CommonResp;
import com.project.hjxwiki.resp.EbookResp;
import com.project.hjxwiki.resp.PageResp;
import com.project.hjxwiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jinxin Hu
 * @version 1.0
 */

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
