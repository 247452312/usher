package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OutApiAssembler;
import top.uhyils.usher.dao.OutApiDao;
import top.uhyils.usher.pojo.DO.OutApiDO;
import top.uhyils.usher.pojo.DTO.OutApiDTO;
import top.uhyils.usher.pojo.entity.OutApi;
import top.uhyils.usher.repository.OutApiRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 开放api(OutApi)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分11秒
 */
@Repository
public class OutApiRepositoryImpl extends AbstractRepository<OutApi, OutApiDO, OutApiDao, OutApiDTO, OutApiAssembler> implements OutApiRepository {

    protected OutApiRepositoryImpl(OutApiAssembler convert, OutApiDao dao) {
        super(convert, dao);
    }


}
