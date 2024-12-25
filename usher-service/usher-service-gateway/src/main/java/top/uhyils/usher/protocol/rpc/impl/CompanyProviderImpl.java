package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.protocol.rpc.CompanyProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.CompanyService;

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

