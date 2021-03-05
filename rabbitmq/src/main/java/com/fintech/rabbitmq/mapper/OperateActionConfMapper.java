package com.fintech.rabbitmq.mapper;

import com.fintech.rabbitmq.entity.OperateActionConf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperateActionConfMapper
{

    int insertSelective(OperateActionConf record);

    OperateActionConf selectByPrimaryKey(Integer id);

    List<OperateActionConf> selectAllByCodeAndFlag(@Param("code") String code, @Param("flag") Integer flag);

    List<String> selectDistinctClazzByCodeAndClazz(@Param("code") String code, @Param("clazz") String clazz);

    List<OperateActionConf> selectAllByCodeAndProductId(@Param("code") String code, @Param("productId") String productId);

}
