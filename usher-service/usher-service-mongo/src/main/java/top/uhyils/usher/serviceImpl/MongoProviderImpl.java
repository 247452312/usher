package top.uhyils.usher.serviceImpl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.mongo.MongoManager;
import top.uhyils.usher.pojo.DTO.request.NameRequest;
import top.uhyils.usher.pojo.cqe.command.base.AddCommand;
import top.uhyils.usher.protocol.rpc.MongoProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.MD5Util;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 07时08分
 */
@RpcService
public class MongoProviderImpl implements MongoProvider {

    @Autowired
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
