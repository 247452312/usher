package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AlgorithmAssembler;
import team.opentech.usher.dao.AlgorithmDao;
import team.opentech.usher.pojo.DO.AlgorithmDO;
import team.opentech.usher.pojo.DTO.AlgorithmDTO;
import team.opentech.usher.pojo.entity.Algorithm;
import team.opentech.usher.repository.AlgorithmRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 算法表(Algorithm)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分07秒
 */
@Repository
public class AlgorithmRepositoryImpl extends AbstractRepository<Algorithm, AlgorithmDO, AlgorithmDao, AlgorithmDTO, AlgorithmAssembler> implements AlgorithmRepository {

    protected AlgorithmRepositoryImpl(AlgorithmAssembler convert, AlgorithmDao dao) {
        super(convert, dao);
    }


}
