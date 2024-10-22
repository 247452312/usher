package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.SoftwareAssembler;
import team.opentech.usher.dao.SoftwareDao;
import team.opentech.usher.pojo.DO.SoftwareDO;
import team.opentech.usher.pojo.DTO.SoftwareDTO;
import team.opentech.usher.pojo.entity.Software;
import team.opentech.usher.repository.SoftwareRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 中间件表(Software)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分21秒
 */
@Repository
public class SoftwareRepositoryImpl extends AbstractRepository<Software, SoftwareDO, SoftwareDao, SoftwareDTO, SoftwareAssembler> implements SoftwareRepository {

    protected SoftwareRepositoryImpl(SoftwareAssembler convert, SoftwareDao dao) {
        super(convert, dao);
    }


}
