package team.opentech.usher.serviceImpl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.mongo.MongoManager;
import team.opentech.usher.pojo.DTO.request.NameRequest;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.protocol.rpc.MongoProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.MD5Util;
import java.util.UUID;
import javax.annotation.Resource;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 07时08分
 */
@RpcService
public class MongoProviderImpl implements MongoProvider {

    @Resource
    private MongoManager mongoManager;

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public String add(AddCommand<String> request) {
        String file = request.getDto();
        String uuid = UUID.randomUUID().toString();
        String md5 = MD5Util.MD5Encode(uuid);
        boolean b1 = mongoManager.addFile(md5, file);
        Asserts.assertTrue(b1, "mongoDB插入文件报错");
        return md5;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public Boolean delete(NameRequest request) {
        String name = request.getName();
        boolean b = mongoManager.removeFile(name);
        return b;
    }

    @Override
    public String getByFileName(NameRequest request) {
        String name = request.getName();
        return mongoManager.getFile(name);
    }


}
