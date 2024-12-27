package top.uhyils.usher.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ServerAssembler;
import top.uhyils.usher.pojo.DO.ServerDO;
import top.uhyils.usher.pojo.DTO.ServerDTO;
import top.uhyils.usher.pojo.DTO.request.TestConnByDataRequest;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.entity.Server;
import top.uhyils.usher.repository.ServerRepository;
import top.uhyils.usher.service.ServerService;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SshUtils;

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
