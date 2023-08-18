package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetByArgsAndGroupQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * api表(Api)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分47秒
 */
public interface ApiProvider extends DTOProvider<ApiDTO> {


    /**
     * 获取所有的指定组下的api
     *
     * @param request 筛选信息
     *
     * @return 所有的指定组下的api
     */
    Page<ApiDTO> getByArgsAndGroup(GetByArgsAndGroupQuery request);

}

