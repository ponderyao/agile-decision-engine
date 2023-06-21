package io.github.ponderyao.decision.entity;

import java.util.Date;

/**
 * BaseEntity
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class BaseEntity {
    
    /** 创建时间 */
    private Date createTime;
    
    /** 修改时间 */
    private Date updateTime;
    
    /** 生效状态 */
    private Boolean status;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
