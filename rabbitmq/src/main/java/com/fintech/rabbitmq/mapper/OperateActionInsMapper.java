package com.fintech.rabbitmq.mapper;

import com.fintech.rabbitmq.entity.OperateActionIns;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperateActionInsMapper
{

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OperateActionIns record);

}
