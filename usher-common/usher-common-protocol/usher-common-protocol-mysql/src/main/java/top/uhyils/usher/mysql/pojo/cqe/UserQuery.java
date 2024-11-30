package top.uhyils.usher.mysql.pojo.cqe;

import top.uhyils.usher.pojo.cqe.query.base.AbstractQuery;

/**
 * 查询用户表数据
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 10时59分
 */
public class UserQuery extends AbstractQuery {

    /**
     * 用户名(模糊)
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
