package com.hicon.v1.hotword.mapper;

import com.hicon.v1.hotword.bean.HotwordBean;

import java.util.List;

public interface HotwordSqlMapper {
    /**
     * 获取热搜词列表页数据
     * @param bean
     * @return
     * @throws Exception
     */
    List<?> getHotList(HotwordBean bean) throws Exception;

    /**
     * 获取热搜词列表页数据
     * @param bean
     * @return
     * @throws Exception
     */
     List<?> getHotwordList(HotwordBean bean) throws Exception;

    /**
     * 修改状态
     * @param bean
     * @return
     * @throws Exception
     */
    Integer modifyStatus(HotwordBean bean ) throws Exception;

    /**
     * 批量修改状态
     * @param bean
     * @return
     * @throws Exception
     */
    Integer bathModifyStatus(HotwordBean bean) throws Exception;

    /**
     * 批量删除
     * @param bean
     * @return
     * @throws Exception
     */
    Integer bathDel(HotwordBean bean) throws Exception;

    /**
     * 编辑热搜词
     * @param bean
     * @return
     * @throws Exception
     */
    Integer update(HotwordBean bean) throws  Exception;

    /**
     * 新增热搜词
     * @param bean
     * @return
     * @throws Exception
     */
    Integer addHotWord(HotwordBean bean) throws Exception;

    /**
     * 删除热搜词
     * @param bean
     * @return
     * @throws Exception
     */
    Integer delHotword(HotwordBean bean) throws Exception;

    /**
     * 获取热搜词总数量
     * @param bean
     * @return
     * @throws Exception
     */
    Integer getHotwordsCount(HotwordBean bean) throws Exception;
}
