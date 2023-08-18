package team.opentech.usher.facade.impl;

import team.opentech.usher.annotation.Facade;
import team.opentech.usher.facade.DictFacade;
import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.request.GetByCodeQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.protocol.rpc.DictProvider;
import team.opentech.usher.rpc.annotation.RpcReference;
import team.opentech.usher.util.DefaultCQEBuildUtil;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 10时32分
 */
@Facade
public class DictFacadeImpl implements DictFacade {


    @RpcReference
    private DictProvider dictProvider;

    @Override
    public List<DictItemDTO> getByCode(String code) {
        GetByCodeQuery request = new GetByCodeQuery();
        DefaultCQE adminDefaultCQE = DefaultCQEBuildUtil.getAdminDefaultCQE();
        request.setUser(adminDefaultCQE.getUser());
        request.setCode(code);
        return dictProvider.getByCode(request);
    }
}
