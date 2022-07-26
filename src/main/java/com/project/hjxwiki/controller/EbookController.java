package com.project.hjxwiki.controller;

import com.project.hjxwiki.req.EbookQueryReq;
import com.project.hjxwiki.req.EbookSaveReq;
import com.project.hjxwiki.resp.CommonResp;
import com.project.hjxwiki.resp.EbookQueryResp;
import com.project.hjxwiki.resp.PageResp;
import com.project.hjxwiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public CommonResp list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        //如果是'headers: Object { Accept: "application/json, text/plain, */*" }'方式提交, 需要添加@RequestBody使得提交方式变为json
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    //如果要传入id需要在参数列表中使用@PathVariable定义.这样参数列表中的id会自动映射传入的id
    //删除需要用DeleteMapping
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        //如果是'headers: Object { Accept: "application/json, text/plain, */*" }'方式提交, 需要添加@RequestBody使得提交方式变为json
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
