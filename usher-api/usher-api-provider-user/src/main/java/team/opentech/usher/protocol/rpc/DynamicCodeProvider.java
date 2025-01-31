package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.DynamicCodeDTO;
import team.opentech.usher.pojo.DTO.request.GetByGroupIdQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;
import java.util.List;

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
