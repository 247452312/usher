package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ServerAssembler;
import team.opentech.usher.dao.ServerDao;
import team.opentech.usher.pojo.DO.ServerDO;
import team.opentech.usher.pojo.DTO.ServerDTO;
import team.opentech.usher.pojo.entity.Server;
import team.opentech.usher.repository.ServerRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


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
