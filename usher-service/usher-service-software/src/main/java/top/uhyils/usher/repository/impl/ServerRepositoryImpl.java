package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ServerAssembler;
import top.uhyils.usher.dao.ServerDao;
import top.uhyils.usher.pojo.DO.ServerDO;
import top.uhyils.usher.pojo.DTO.ServerDTO;
import top.uhyils.usher.pojo.entity.Server;
import top.uhyils.usher.repository.ServerRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 服务器表(Server)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分17秒
 */
@Repository
public class ServerRepositoryImpl extends AbstractRepository<Server, ServerDO, ServerDao, ServerDTO, ServerAssembler> implements ServerRepository {

    protected ServerRepositoryImpl(ServerAssembler convert, ServerDao dao) {
        super(convert, dao);
    }


    @Override
    public List<Server> findServersIdAndName() {
        List<ServerDO> serversIdAndName = dao.getServersIdAndName();
        return assembler.listToEntity(serversIdAndName);
    }
}
