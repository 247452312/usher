package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.AlgorithmDTO;
import team.opentech.usher.pojo.DTO.request.CellAlgorithmRequest;
import team.opentech.usher.pojo.DTO.response.CellAlgorithmResponse;
import team.opentech.usher.protocol.rpc.AlgorithmProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AlgorithmService;
import team.opentech.usher.service.BaseDoService;
import org.springframework.beans.factory.annotation.Autowired;

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

