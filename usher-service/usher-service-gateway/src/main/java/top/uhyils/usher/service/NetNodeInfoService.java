package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;

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
}
