package top.uhyils.usher.pojo.entity;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.codec.binary.Base64;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.context.UserInfoHelper;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.pojo.DO.CompanyDO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.pojo.entity.type.Password;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.util.Asserts;

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
     * 根据ak填充
     *
     * @param companyRepository
     */
    public void completionByAk(CompanyRepository companyRepository) {
        Company company = companyRepository.findByAk(toData().map(CompanyDO::getAk).orElse(null));
        Asserts.assertTrue(company != null, "未找到ak对应的公司,请联系管理");
        company.toData().ifPresent(t -> {
            this.data = t;
            setUnique(t.getId());
        });

    }

    /**
     * 验证mysql登录时 sk是否一致
     *
     * @param seed
     * @param challenge
     *
     * @return
     */
    public boolean checkSkByMysqlChallenge(byte[] seed, String challenge) {
        // 1.1 密码解密
        Password realPassword = new Password(toData().get().getSk());
        // 1.2 密码再次加密为mysql SHA-1
        String decodePassword = realPassword.decode();
        byte[] bytes = MysqlUtil.encodePassword(decodePassword.getBytes(StandardCharsets.UTF_8), seed);
        String userPassword = Base64.encodeBase64String(bytes);
        return Objects.equals(userPassword, challenge);
    }

    public UserDTO mysqlLogin() {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        String ip = mysqlTcpLink.findLocalAddress().getAddress().getHostName();
        UserInfoHelper.setIp(ip);
        CompanyDO dataAndValidate = toDataAndValidate();
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(dataAndValidate.getPersonName());
        userDTO.setUsername(dataAndValidate.getName());
        userDTO.setPhone(dataAndValidate.getPersonPhone());
        userDTO.setStatus(1);
        userDTO.setIp(ip);
        userDTO.setId(getUnique().get());
        UserInfoHelper.setUser(userDTO);
        return userDTO;

    }
}
