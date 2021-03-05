package com.frame.resource.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.resource.entity.TRCarInfo;
import com.frame.resource.service.TRCarInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 车辆（挂车）表(TRCarInfo)表控制层
 *
 * @author makejava
 * @since 2021-02-02 16:32:33
 */
@RestController
@RequestMapping("car")
public class TRCarInfoController {
    /**
     * 服务对象
     */
    @Resource
    private TRCarInfoService tRCarInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param body 主键
     * @return 单条数据
     */
    @PostMapping("selectOne")
    public TRCarInfo selectOne(@RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        return this.tRCarInfoService.queryById(json.getInteger("id"));
    }

}