package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.AlgorithmDTO;
import top.uhyils.usher.pojo.DTO.request.CellAlgorithmRequest;
import top.uhyils.usher.pojo.DTO.response.CellAlgorithmResponse;
import top.uhyils.usher.protocol.rpc.AlgorithmProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.AlgorithmService;
import top.uhyils.usher.service.BaseDoService;

/**
 * 算法表(Algorithm)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分08秒
 */
@RpcService
public class AlgorithmProviderImpl extends BaseDefaultProvider<AlgorithmDTO> implements AlgorithmProvider {


    @Autowired
    private AlgorithmService service;

    @Override
    public CellAlgorithmResponse cellAlgorithm(CellAlgorithmRequest request) {
        CellAlgorithmResponse result = service.cellAlgorithm(request);
        return result;
    }

    @Override
    protected BaseDoService<AlgorithmDTO> getService() {
        return service;
    }

}

