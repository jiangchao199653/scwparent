package com.offcn.project.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ProjectReturnVo {

    private String projectToken;

    @ApiModelProperty(value = "回报类型：0-虚拟回报 1-实物回报",required = true)
    private Byte type;

    @ApiModelProperty(value = "支持金额",required = true,example = "0")
    private Integer supportmoney;

    @ApiModelProperty(value = "回报内容",required = true)
    private String content;

    @ApiModelProperty(value = "单笔限购",required = true,example = "0")
    private Integer signalpurchase;

    @ApiModelProperty(value = "限购数量",required = true,example = "0")
    private Integer purchase;

    @ApiModelProperty(value = "运费",required = true,example = "0")
    private Integer freight;

    @ApiModelProperty(value = "发票 0-不开发票 1-开发票",required = true)
    private Byte invoice;

    @ApiModelProperty(value = "回报时间，天数",required = true,example = "0")
    private Integer rtndate;
    @ApiModelProperty(value = "购买数量",required = true,example = "0")
    private Integer count;

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Integer supportmoney) {
        this.supportmoney = supportmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSignalpurchase() {
        return signalpurchase;
    }

    public void setSignalpurchase(Integer signalpurchase) {
        this.signalpurchase = signalpurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Byte getInvoice() {
        return invoice;
    }

    public void setInvoice(Byte invoice) {
        this.invoice = invoice;
    }

    public Integer getRtndate() {
        return rtndate;
    }

    public void setRtndate(Integer rtndate) {
        this.rtndate = rtndate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
