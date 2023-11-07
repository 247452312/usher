package team.opentech.usher.protocol.rpc.impl;

import javax.annotation.Resource;
import team.opentech.usher.pojo.DTO.CompanyPowerDTO;
import team.opentech.usher.protocol.rpc.CompanyPowerProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.CompanyPowerService;

/**
 * 厂商接口调用权限表(CompanyPower)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class CompanyPowerProviderImpl extends BaseDefaultProvider<CompanyPowerDTO> implements CompanyPowerProvider {


    @Resource
    private CompanyPowerService service;


    @Override
    protected BaseDoService<CompanyPowerDTO> getService() {
        return service;
    }

}

