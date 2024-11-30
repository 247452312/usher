package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.ServerDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.ServerRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SshUtils;

/**
 * 服务器表(Server)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 08时42分15秒
 */
public class Server extends AbstractDoEntity<ServerDO> {

    @Default
    public Server(ServerDO data) {
        super(data);
    }

    public Server(Long id, ServerRepository rep) {
        super(id, new ServerDO());
        completion(rep);
    }

    public Server(Long id) {
        super(id, new ServerDO());
    }

    public Boolean testConn() {
        ServerDO serverDO = toData().orElseThrow(() -> Asserts.makeException("未找到data"));
        return SshUtils.testConn(serverDO.getIp(), serverDO.getPort(), serverDO.getUsername(), serverDO.getPassword());
    }
}
