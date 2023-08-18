package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.DictAssembler;
import team.opentech.usher.dao.DictDao;
import team.opentech.usher.pojo.DO.DictDO;
import team.opentech.usher.pojo.DTO.DictDTO;
import team.opentech.usher.pojo.entity.Dict;
import team.opentech.usher.repository.DictRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 数据字典(Dict)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分37秒
 */
@Repository
public class DictRepositoryImpl extends AbstractRepository<Dict, DictDO, DictDao, DictDTO, DictAssembler> implements DictRepository {

    protected DictRepositoryImpl(DictAssembler convert, DictDao dao) {
        super(convert, dao);
    }

}
