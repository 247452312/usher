package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.request.NameRequest;
import top.uhyils.usher.pojo.cqe.command.base.AddCommand;
import top.uhyils.usher.protocol.rpc.base.BaseProvider;

/**
 * mongo接口
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 06时35分
 */
public interface MongoProvider extends BaseProvider {

    /**
     * 添加
     *
     * @param request base64 图片
     *
     * @return fileName
     */
    String add(AddCommand<String> request);

    /**
     * 删除
     *
     * @param request fileName
     *
     * @return 是否成功
     */
    Boolean delete(NameRequest request);


    /**
     * 根据fileName精准查询
     *
     * @param request fileName
     *
     * @return base64
     */
    String getByFileName(NameRequest request);
}
