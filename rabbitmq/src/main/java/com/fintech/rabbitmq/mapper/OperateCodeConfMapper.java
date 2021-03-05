package com.fintech.rabbitmq.mapper;

import com.fintech.rabbitmq.entity.OperateCodeConf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperateCodeConfMapper
{

    OperateCodeConf selectOneByCode(@Param("code")String code);

}
