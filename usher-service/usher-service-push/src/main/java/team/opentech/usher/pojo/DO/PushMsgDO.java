package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * 推送日志表(PushMsg)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月11日 10时16分24秒
 */
@TableName(value = "sys_push_msg")
public class PushMsgDO extends BaseDO {

    private static final long serialVersionUID = 405566975672895779L;


    @TableField
    private String content;

    @TableField
    private Boolean success;

    @TableField
    private Long target;

    @TableField
    private String title;

    @TableField
    private Integer type;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}