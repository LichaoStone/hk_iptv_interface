package com.hicon.v1.hotword.service.impl;

import com.hicon.v1.hotword.bean.HotwordBean;
import com.hicon.v1.hotword.mapper.HotwordSqlMapper;
import com.hicon.v1.hotword.service.HotwordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotwordServiceImpl implements HotwordService {

    @Resource
    private HotwordSqlMapper hotwordSqlMapper;

    @Override
    public List<?> getHotList(HotwordBean bean) throws Exception {
        return hotwordSqlMapper.getHotList(bean);
    }

    @Override
    public List<?> getHotwordList(HotwordBean bean) throws Exception {
        return hotwordSqlMapper.getHotwordList(bean);
    }

    @Override
    public Integer modifyStatus(HotwordBean bean) throws Exception {
        return "-1".equals(bean.getStatus())
                ?hotwordSqlMapper.delHotword(bean)
                :hotwordSqlMapper.modifyStatus(bean);
    }

    @Override
    public Integer bathModifyStatus(HotwordBean bean) throws Exception {
        return "-1".equals(bean.getStatus())
                ?hotwordSqlMapper.bathDel(bean)
                :hotwordSqlMapper.bathModifyStatus(bean);
    }

    @Override
    public Integer update(HotwordBean bean) throws Exception {
        return hotwordSqlMapper.update(bean);
    }

    @Override
    public Integer addHotWord(HotwordBean bean) throws Exception {
        return hotwordSqlMapper.addHotWord(bean);
    }

    @Override
    public Integer getHotwordsCount(HotwordBean bean) throws Exception {
        return hotwordSqlMapper.getHotwordsCount(bean);
    }
}
