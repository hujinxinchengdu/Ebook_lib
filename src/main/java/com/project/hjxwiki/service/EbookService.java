package com.project.hjxwiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.hjxwiki.domain.Ebook;
import com.project.hjxwiki.domain.EbookExample;
import com.project.hjxwiki.mapper.EbookMapper;
import com.project.hjxwiki.req.EbookQueryReq;
import com.project.hjxwiki.req.EbookSaveReq;
import com.project.hjxwiki.resp.EbookQueryResp;
import com.project.hjxwiki.resp.PageResp;
import com.project.hjxwiki.util.CopyUtil;
import com.project.hjxwiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinxin Hu
 * @version 1.0
 */
@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);

        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
//            respList.add(ebookResp);
//        }

        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * Save
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        //由于我们的update和insert都需要用到这个api所以需要对传入的参数做判断, 来确定是添加一个新的数据还是,更新
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            //using雪花算法
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }

    }
}
