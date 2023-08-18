package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.BlackListAssembler;
import team.opentech.usher.dao.BlackListDao;
import team.opentech.usher.pojo.DO.BlackListDO;
import team.opentech.usher.pojo.DTO.BlackListDTO;
import team.opentech.usher.pojo.entity.BlackList;
import team.opentech.usher.repository.BlackListRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


/**
 * 黑名单(BlackList)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
@Repository
public class BlackListRepositoryImpl extends AbstractRepository<BlackList, BlackListDO, BlackListDao, BlackListDTO, BlackListAssembler> implements BlackListRepository {

    protected BlackListRepositoryImpl(BlackListAssembler convert, BlackListDao dao) {
        super(convert, dao);
    }


    @Override
    public List<String> findAllIpBlackList() {
        return dao.getAllIpBlackList();
    }
}
