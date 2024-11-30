package top.uhyils.usher.pojo.DO.base;

import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.util.Asserts;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 17时49分
 */
public class BaseView implements BaseDbSaveable {

    @Override
    public void preInsert(DefaultCQE request) {
        Asserts.throwException("视图不能修改");
    }

    @Override
    public void preInsert(UserDTO userDO) {
        Asserts.throwException("视图不能修改");
    }

    @Override
    public void preInsert() {
        Asserts.throwException("视图不能修改");
    }

    @Override
    public void preUpdate(DefaultCQE request) {
        Asserts.throwException("视图不能修改");
    }

    @Override
    public void preUpdate(UserDTO userDO) {
        Asserts.throwException("视图不能修改");
    }

    @Override
    public void preUpdate() {
        Asserts.throwException("视图不能修改");
    }
}
