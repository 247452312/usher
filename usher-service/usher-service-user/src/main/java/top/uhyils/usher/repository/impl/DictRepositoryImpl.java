package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.DictAssembler;
import top.uhyils.usher.dao.DictDao;
import top.uhyils.usher.pojo.DO.DictDO;
import top.uhyils.usher.pojo.DTO.DictDTO;
import top.uhyils.usher.pojo.entity.Dict;
import top.uhyils.usher.repository.DictRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
