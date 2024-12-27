package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.SoftwareAssembler;
import top.uhyils.usher.dao.SoftwareDao;
import top.uhyils.usher.pojo.DO.SoftwareDO;
import top.uhyils.usher.pojo.DTO.SoftwareDTO;
import top.uhyils.usher.pojo.entity.Software;
import top.uhyils.usher.repository.SoftwareRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
