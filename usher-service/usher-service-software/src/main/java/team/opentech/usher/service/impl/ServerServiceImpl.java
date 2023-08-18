package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ServerAssembler;
import team.opentech.usher.pojo.DO.ServerDO;
import team.opentech.usher.pojo.DTO.ServerDTO;
import team.opentech.usher.pojo.DTO.request.TestConnByDataRequest;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.entity.Server;
import team.opentech.usher.repository.ServerRepository;
import team.opentech.usher.service.ServerService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.SshUtils;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 服务器表(Server)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分17秒
 */
@Service
@ReadWriteMark(tables = {"sys_server"})
public class ServerServiceImpl extends AbstractDoService<ServerDO, Server, ServerDTO, ServerRepository, ServerAssembler> implements ServerService {

    public ServerServiceImpl(ServerAssembler assembler, ServerRepository repository) {
        super(assembler, repository);
    }


    @Override
    public Boolean testConnByData(TestConnByDataRequest request) {
        return SshUtils.testConn(request.getIp(), request.getPort(), request.getUsername(), request.getPassword());
    }

    @Override
    public Boolean testConnById(IdCommand request) {
        Server server = new Server(request.getId(), rep);
        return server.testConn();
    }

    @Override
    public List<ServerDTO> getServersIdAndName(DefaultCQE request) {
        List<Server> servers = rep.findServersIdAndName();
        return assem.listEntityToDTO(servers);
    }

    @Override
    public String getNameById(IdQuery request) {
        Server server = new Server(request.getId(), rep);
        return server.toData().map(ServerDO::getName).orElseThrow(() -> Asserts.makeException("未找到data"));
    }
}
