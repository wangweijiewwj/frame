package com.frame.resource.service;

import com.frame.resource.entity.TRCarInfo;
import java.util.List;

/**
 * 车辆（挂车）表(TRCarInfo)表服务接口
 *
 * @author makejava
 * @since 2021-02-02 16:32:31
 */
public interface TRCarInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TRCarInfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TRCarInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tRCarInfo 实例对象
     * @return 实例对象
     */
    TRCarInfo insert(TRCarInfo tRCarInfo);

    /**
     * 修改数据
     *
     * @param tRCarInfo 实例对象
     * @return 实例对象
     */
    TRCarInfo update(TRCarInfo tRCarInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}