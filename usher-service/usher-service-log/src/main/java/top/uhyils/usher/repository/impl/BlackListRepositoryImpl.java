package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.BlackListAssembler;
import top.uhyils.usher.dao.BlackListDao;
import top.uhyils.usher.pojo.DO.BlackListDO;
import top.uhyils.usher.pojo.DTO.BlackListDTO;
import top.uhyils.usher.pojo.entity.BlackList;
import top.uhyils.usher.repository.BlackListRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
