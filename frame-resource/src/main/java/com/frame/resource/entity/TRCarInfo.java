package com.frame.resource.entity;

import java.io.Serializable;

/**
 * 车辆（挂车）表(TRCarInfo)实体类
 *
 * @author makejava
 * @since 2021-02-02 16:32:28
 */
public class TRCarInfo implements Serializable {
    private static final long serialVersionUID = -70458533938535760L;
    /**
    * 主键自增ID
    */
    private Integer id;
    /**
    * 来源（1-自注册 2-客服添加）
    */
    private Integer resource;
    /**
    * 类型（1-车辆 2-挂车） 
    */
    private Integer type;
    /**
    * 类别（1-自有 2-外协 ）  
    */
    private Integer classify;
    /**
    * 车牌号,当为挂车时，此字段存罐号
    */
    private String carNumber;
    /**
    * 挂车号
    */
    private String guaNumber;
    /**
    * 车辆识别代码
    */
    private String driverNum;
    /**
    *  罐体类型 1铁罐、2不锈钢罐、3铝合金罐、4内衬塑罐
    */
    private Integer guaType;
    /**
    * 荷载吨数
    */
    private Integer carTon;
    /**
    * 车辆类型1 重型半挂牵引车2中型半挂牵引车3轻型半挂牵引车

    */
    private Integer carType;
    /**
    * 装口类型1上装口 2下装口 都有时1,2
    */
    private String loading;
    /**
    * 审核意见
    */
    private String remake;
    /**
    * 状态（0-审核中  1-审核通过  2-审核未通过）
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Long createTime;
    /**
    * 创建人
    */
    private Integer createUserId;
    /**
    * 更新时间
    */
    private Long updateTime;
    /**
    * 更新人
    */
    private Integer updateUserId;
    /**
    * 扩展字段1
    */
    private Integer ext1;
    /**
    * 扩展字段2
    */
    private String ext2;
    /**
    * 设备号
    */
    private String deviceNo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getGuaNumber() {
        return guaNumber;
    }

    public void setGuaNumber(String guaNumber) {
        this.guaNumber = guaNumber;
    }

    public String getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(String driverNum) {
        this.driverNum = driverNum;
    }

    public Integer getGuaType() {
        return guaType;
    }

    public void setGuaType(Integer guaType) {
        this.guaType = guaType;
    }

    public Integer getCarTon() {
        return carTon;
    }

    public void setCarTon(Integer carTon) {
        this.carTon = carTon;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getExt1() {
        return ext1;
    }

    public void setExt1(Integer ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

}