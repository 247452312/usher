package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.ServerDO;
import top.uhyils.usher.pojo.entity.Server;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
