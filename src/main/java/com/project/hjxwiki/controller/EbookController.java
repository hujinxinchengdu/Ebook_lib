package com.project.hjxwiki.controller;

import com.project.hjxwiki.req.EbookQueryReq;
import com.project.hjxwiki.req.EbookSaveReq;
import com.project.hjxwiki.resp.CommonResp;
import com.project.hjxwiki.resp.EbookQueryResp;
import com.project.hjxwiki.resp.PageResp;
import com.project.hjxwiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

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
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        //如果是'headers: Object { Accept: "application/json, text/plain, */*" }'方式提交, 需要添加@RequestBody使得提交方式变为json
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
