package com.hicon.v1.hotword.controller;

import com.hicon.frame.BaseController;
import com.hicon.frame.apiversion.ApiVersion;
import com.hicon.utils.DateUtil;
import com.hicon.utils.PkCreat;
import com.hicon.v1.hotword.bean.HotwordBean;
import com.hicon.v1.hotword.service.HotwordService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@ApiVersion(1)
@RequestMapping("/{version}/search")
public class HotwordController extends BaseController {
    public static final Logger logger = Logger.getLogger(HotwordController.class);

    @Resource
    HotwordService hotwordService;


        /**
         * 获取热搜词
         * @param request
         * @param response
         */
        @RequestMapping(value="/hotList",method= RequestMethod.GET)
        @ResponseBody
        private void hotList(HttpServletRequest request, HttpServletResponse response){
            try{
                HotwordBean hotwordBean=new HotwordBean();
                List<?> dataList=hotwordService.getHotwordList(hotwordBean);
                sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,dataList),response);
            }catch(Exception e) {
                logger.error("【热搜词管理】获取热搜词数据失败:",e);
                sendJson(getJson(CODE.FAIL,RET.FAIL),response);
            }
        }



    /**
     * 获取热搜词
     * @param request
     * @param response
     */
    @RequestMapping(value="/getHotwordList",method= RequestMethod.GET)
    @ResponseBody
    private void getHotwordList(HttpServletRequest request, HttpServletResponse response){
        String hotwords=request.getParameter("hotwords");
        String status=request.getParameter("status");

        logger.info("【热搜词管理】获取热搜词列表页数据:hotwords="+hotwords+",status="+status);

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.setHotWords(hotwords);
            hotwordBean.setStatus(status);

            List<?> dataList=hotwordService.getHotwordList(hotwordBean);
            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,dataList),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】获取热搜词数据失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }

    /**
     * 获取热搜词数量
     * @param request
     * @param response
     */
    @RequestMapping(value="/getHotwordsCount",method= RequestMethod.GET)
    @ResponseBody
    private void getHotwordsCount(HttpServletRequest request, HttpServletResponse response){
        String status=request.getParameter("status");

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.setStatus(status);
            Integer count=hotwordService.getHotwordsCount(hotwordBean);
            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,String.valueOf(count)),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】获取热搜词数量失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }


    /**
     * 修改状态
     * @param request
     * @param response
     */
    @RequestMapping(value="/modifyStatus",method= RequestMethod.GET)
    @ResponseBody
    private void modifyStatus(HttpServletRequest request, HttpServletResponse response){
        String status=request.getParameter("status");
        String tHotKey=request.getParameter("tHotKey");

        logger.info("【热搜词管理】修改热搜词状态:tHotKey="+tHotKey+",status="+status);

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.settHotKey(tHotKey);
            hotwordBean.setStatus(status);

            hotwordService.modifyStatus(hotwordBean);
            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】修改热搜词状态失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }


    /**
     * 修改热搜词
     * @param request
     * @param response
     */
    @RequestMapping(value="/update",method= RequestMethod.GET)
    @ResponseBody
    private void update(HttpServletRequest request, HttpServletResponse response){
        String hotwords=request.getParameter("hotwords");
        String tHotKey=request.getParameter("tHotKey");

        logger.info("【热搜词管理】修改热搜词:tHotKey="+tHotKey+",hotwords="+hotwords);

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.settHotKey(tHotKey);
            hotwordBean.setHotWords(hotwords);

            hotwordService.update(hotwordBean);

            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】修改热搜词失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }


    /**
     * 修改热搜词
     * @param request
     * @param response
     */
    @RequestMapping(value="/addHotword",method= RequestMethod.GET)
    @ResponseBody
    private void addHotword(HttpServletRequest request, HttpServletResponse response){
        String hotwords=request.getParameter("hotwords");

        logger.info("【热搜词管理】修改热搜词:hotwords="+hotwords);

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.settHotKey(PkCreat.getTablePk());
            hotwordBean.setHotWords(hotwords);
            hotwordBean.setStatus("2");  //默认禁用
            hotwordBean.setOrderNum("10000"); //默认10000
            hotwordBean.setCreator("111");
            String time= DateUtil.getTimeToSec();
            hotwordBean.setCreateTime(time);
            hotwordBean.setUpdateTime(time);

            hotwordService.addHotWord(hotwordBean);

            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】修改热搜词失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }

    /**
     * 批量修改热搜词
     * @param request
     * @param response
     */
    @RequestMapping(value="/bathModifyStatus",method= RequestMethod.GET)
    @ResponseBody
    private void bathModifyStatus(HttpServletRequest request, HttpServletResponse response){
        String  keys=request.getParameter("keys");
        String  status=request.getParameter("status");

        logger.info("【热搜词管理】修改热搜词:keys="+keys+",status="+status);

        try{
            HotwordBean hotwordBean=new HotwordBean();
            hotwordBean.setKeyList(Arrays.asList(keys.split(",")));
            hotwordBean.setStatus(status);

            hotwordService.bathModifyStatus(hotwordBean);

            sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
        }catch(Exception e) {
            logger.error("【热搜词管理】修改热搜词失败:",e);
            sendJson(getJson(CODE.FAIL,RET.FAIL),response);
        }
    }


}
