package com.offcn.project.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("项目基本信息")
public class ProjectBaseInfoVo {

    @ApiModelProperty("项目之前的临时token")
    private String projectToken;// 项目的临时token

    @ApiModelProperty("项目的分类id")
    private List<Integer> typeids; // 项目的分类id

    @ApiModelProperty("项目的标签id")
    private List<Integer> tagids; // 项目的标签id

    @ApiModelProperty("项目名称")
    private String name;// 项目名称

    @ApiModelProperty("项目简介")
    private String remark;// 项目简介

    @ApiModelProperty(value = "筹资金额",example = "0")
    private Integer money;// 筹资金额

    @ApiModelProperty(value = "筹资天数",example = "0")
    private Integer day;// 筹资天数

    @ApiModelProperty("项目头部图片")
    private String headerImage;// 项目头部图片

    @ApiModelProperty("项目详情图片")
    private List<String> detailsImage;// 项目详情图片

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public List<Integer> getTypeids() {
        return typeids;
    }

    public void setTypeids(List<Integer> typeids) {
        this.typeids = typeids;
    }

    public List<Integer> getTagids() {
        return tagids;
    }

    public void setTagids(List<Integer> tagids) {
        this.tagids = tagids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public List<String> getDetailsImage() {
        return detailsImage;
    }

    public void setDetailsImage(List<String> detailsImage) {
        this.detailsImage = detailsImage;
    }
}
