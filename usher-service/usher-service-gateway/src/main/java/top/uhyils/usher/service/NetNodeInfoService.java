package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.cqe.NetNodeCreateCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public interface NetNodeInfoService extends BaseDoService<NetNodeInfoDTO> {


    /**
     * 根据数据库和表名获取表信息
     *
     * @param database
     * @param table
     *
     * @return
     */
    NetNodeInfoDTO findByDatabaseAndTable(String database, String table);

    /**
     * 获取表可操作方式信息
     *
     * @param id
     *
     * @return
     */
    List<NetNodeInfoDetailDTO> findDetailById(Long id);

    /**
     * 根据公司id获取所有节点信息
     *
     * @param idCommand
     *
     * @return
     */
    List<NetNodeInfoDTO> findWithDetailByCompanyId(IdCommand idCommand);


    /**
     * 根据公司id和数据库id获取节点信息
     *
     * @param companyId
     * @param databases
     *
     * @return
     */
    List<NetNodeInfoDTO> findByCompanyIdAndDatabase(Long companyId, List<String> databases);

    /**
     * 添加节点信息
     *
     * @param command
     *
     * @return
     */
    Boolean add(NetNodeCreateCommand command);

}
