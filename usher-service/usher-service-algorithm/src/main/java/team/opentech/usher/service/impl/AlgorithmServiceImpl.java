package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AlgorithmAssembler;
import team.opentech.usher.pojo.DO.AlgorithmDO;
import team.opentech.usher.pojo.DTO.AlgorithmDTO;
import team.opentech.usher.pojo.DTO.request.CellAlgorithmRequest;
import team.opentech.usher.pojo.DTO.response.CellAlgorithmResponse;
import team.opentech.usher.pojo.entity.Algorithm;
import team.opentech.usher.repository.AlgorithmRepository;
import team.opentech.usher.service.AlgorithmService;

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
