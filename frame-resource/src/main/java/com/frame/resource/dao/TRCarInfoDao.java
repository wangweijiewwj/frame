package com.frame.resource.dao;

import com.frame.resource.entity.TRCarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 车辆（挂车）表(TRCarInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-02 16:32:30
 */
@Mapper
public interface TRCarInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TRCarInfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TRCarInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tRCarInfo 实例对象
     * @return 对象列表
     */
    List<TRCarInfo> queryAll(TRCarInfo tRCarInfo);

    /**
     * 新增数据
     *
     * @param tRCarInfo 实例对象
     * @return 影响行数
     */
    int insert(TRCarInfo tRCarInfo);

    /**
     * 修改数据
     *
     * @param tRCarInfo 实例对象
     * @return 影响行数
     */
    int update(TRCarInfo tRCarInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}