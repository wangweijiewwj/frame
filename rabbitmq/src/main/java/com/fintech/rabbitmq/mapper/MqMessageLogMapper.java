package com.fintech.rabbitmq.mapper;

import com.fintech.rabbitmq.entity.MqMessageLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MqMessageLogMapper
{

    int insertSelective(MqMessageLog record);

    MqMessageLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MqMessageLog record);
}
