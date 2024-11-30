package top.uhyils.usher.facade.impl;

import java.util.List;
import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.DictFacade;
import top.uhyils.usher.pojo.DTO.DictItemDTO;
import top.uhyils.usher.pojo.DTO.request.GetByCodeQuery;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.DictProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;
import top.uhyils.usher.util.DefaultCQEBuildUtil;


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
