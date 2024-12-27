package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.DynamicCodeDTO;
import top.uhyils.usher.pojo.DTO.request.GetByGroupIdQuery;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * 动态代码主表(DynamicCode)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年02月11日 18时53分
 */
public interface DynamicCodeProvider extends DTOProvider<DynamicCodeDTO> {

    /**
     * 根据groupId获取动态代码
     *
     * @param query
     *
     * @return
     */
    List<DynamicCodeDTO> getByGroupId(GetByGroupIdQuery query);
}
