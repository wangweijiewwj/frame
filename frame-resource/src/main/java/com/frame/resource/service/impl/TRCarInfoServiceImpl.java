package com.frame.resource.service.impl;

import com.frame.resource.entity.TRCarInfo;
import com.frame.resource.dao.TRCarInfoDao;
import com.frame.resource.service.TRCarInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车辆（挂车）表(TRCarInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-02-02 16:32:32
 */
@Service("tRCarInfoService")
public class TRCarInfoServiceImpl implements TRCarInfoService {
    @Resource
    private TRCarInfoDao tRCarInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TRCarInfo queryById(Integer id) {
        return this.tRCarInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TRCarInfo> queryAllByLimit(int offset, int limit) {
        return this.tRCarInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tRCarInfo 实例对象
     * @return 实例对象
     */
    @Override
    public TRCarInfo insert(TRCarInfo tRCarInfo) {
        this.tRCarInfoDao.insert(tRCarInfo);
        return tRCarInfo;
    }

    /**
     * 修改数据
     *
     * @param tRCarInfo 实例对象
     * @return 实例对象
     */
    @Override
    public TRCarInfo update(TRCarInfo tRCarInfo) {
        this.tRCarInfoDao.update(tRCarInfo);
        return this.queryById(tRCarInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tRCarInfoDao.deleteById(id) > 0;
    }
}