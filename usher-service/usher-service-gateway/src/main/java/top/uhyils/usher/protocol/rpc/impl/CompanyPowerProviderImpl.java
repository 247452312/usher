package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.CompanyPowerDTO;
import top.uhyils.usher.protocol.rpc.CompanyPowerProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.CompanyPowerService;

/**
 * 厂商接口调用权限表(CompanyPower)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class CompanyPowerProviderImpl extends BaseDefaultProvider<CompanyPowerDTO> implements CompanyPowerProvider {


    @Autowired
    private CompanyPowerService service;


    @Override
    protected BaseDoService<CompanyPowerDTO> getService() {
        return service;
    }

}

