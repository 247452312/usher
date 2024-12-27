package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AlgorithmAssembler;
import top.uhyils.usher.dao.AlgorithmDao;
import top.uhyils.usher.pojo.DO.AlgorithmDO;
import top.uhyils.usher.pojo.DTO.AlgorithmDTO;
import top.uhyils.usher.pojo.entity.Algorithm;
import top.uhyils.usher.repository.AlgorithmRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
