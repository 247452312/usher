package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.ApiGroupDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * api组表(ApiGroup)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分51秒
 */
public interface ApiGroupProvider extends DTOProvider<ApiGroupDTO> {

    /**
     * 测试api
     *
     * @param request api id
     *
     * @return 结果
     */
    String test(IdQuery request);

    /**
     * 获取可以被订阅的所有api群
     *
     * @param request 默认请求
     *
     * @return 可以被订阅的api群
     */
    List<ApiGroupDTO> getCanBeSubscribed(DefaultCQE request);
}

