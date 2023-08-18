package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OutApiAssembler;
import team.opentech.usher.dao.OutApiDao;
import team.opentech.usher.pojo.DO.OutApiDO;
import team.opentech.usher.pojo.DTO.OutApiDTO;
import team.opentech.usher.pojo.entity.OutApi;
import team.opentech.usher.repository.OutApiRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
