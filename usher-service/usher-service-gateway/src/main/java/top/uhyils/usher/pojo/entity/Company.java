package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import top.uhyils.usher.pojo.DO.CompanyDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.pojo.entity.type.Password;

/**
 * 厂商表(Company)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class Company extends AbstractDoEntity<CompanyDO> {

    @Default
    public Company(CompanyDO data) {
        super(data);
    }

    public Company(Long id) {
        super(id, new CompanyDO());
    }

    public Company(MysqlAuthCommand command) {
        super(parseCommand(command));
    }

    /**
     * 解析登录请求
     *
     * @param command
     *
     * @return
     */
    private static CompanyDO parseCommand(MysqlAuthCommand command) {
        CompanyDO companyDO = new CompanyDO();
        String ak = command.getUsername();
        companyDO.setAk(ak);
        return companyDO;
    }


    /**
     * 加密密码
     */
    public void encodePassword() {
        CompanyDO dataAndValidate = toDataAndValidate();
        Password password = new Password(dataAndValidate.getSk());
        String encode = password.encode();
        dataAndValidate.setSk(encode);
        onUpdate();
    }
}
