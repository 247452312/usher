package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.protocol.rpc.CompanyProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 厂商表(Company)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class CompanyProviderImpl extends BaseDefaultProvider<CompanyDTO> implements CompanyProvider {


    @Autowired
    private CompanyService service;


    @Override
    protected BaseDoService<CompanyDTO> getService() {
        return service;
    }

}

