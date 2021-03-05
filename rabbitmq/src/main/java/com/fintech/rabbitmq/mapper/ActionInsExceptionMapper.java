package com.fintech.rabbitmq.mapper;


import com.fintech.rabbitmq.entity.ActionInsException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActionInsExceptionMapper
{
    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ActionInsException record);

}
