package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.cqe.CompanyCreateCommand;
import top.uhyils.usher.pojo.cqe.UserQuery;

/**
 * 厂商表(Company)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface CompanyService extends BaseDoService<CompanyDTO> {

    /**
     * 根据ak获取公司
     *
     * @param ak ak
     *
     * @return
     */
    CompanyDTO findByAk(String ak);

    /**
     * 查询用户
     *
     * @param userQuery 查询条件
     *
     * @return
     */
    List<CompanyDTO> queryCompany(UserQuery userQuery);

    /**
     * 创建公司
     *
     * @param command
     *
     * @return
     */
    Boolean create(CompanyCreateCommand command);
}
