package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ServerDO;
import team.opentech.usher.pojo.entity.Server;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 服务器表(Server)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分16秒
 */
public interface ServerRepository extends BaseEntityRepository<ServerDO, Server> {


    /**
     * 获取全部
     *
     * @return
     */
    List<Server> findServersIdAndName();
}
