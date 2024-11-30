package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.AlgorithmAssembler;
import top.uhyils.usher.pojo.DO.AlgorithmDO;
import top.uhyils.usher.pojo.DTO.AlgorithmDTO;
import top.uhyils.usher.pojo.DTO.request.CellAlgorithmRequest;
import top.uhyils.usher.pojo.DTO.response.CellAlgorithmResponse;
import top.uhyils.usher.pojo.entity.Algorithm;
import top.uhyils.usher.repository.AlgorithmRepository;
import top.uhyils.usher.service.AlgorithmService;

/**
 * 算法表(Algorithm)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分08秒
 */
@Service
@ReadWriteMark(tables = {"sys_algorithm"})
public class AlgorithmServiceImpl extends AbstractDoService<AlgorithmDO, Algorithm, AlgorithmDTO, AlgorithmRepository, AlgorithmAssembler> implements AlgorithmService {

    public AlgorithmServiceImpl(AlgorithmAssembler assembler, AlgorithmRepository repository) {
        super(assembler, repository);
    }


    @Override
    public CellAlgorithmResponse cellAlgorithm(CellAlgorithmRequest request) {
        Long algorithmId = request.getAlgorithmId();
        Algorithm algorithm = rep.find(algorithmId);
        Object result = algorithm.cell(request.getRequestBody());
        return CellAlgorithmResponse.build(result);
    }

}
