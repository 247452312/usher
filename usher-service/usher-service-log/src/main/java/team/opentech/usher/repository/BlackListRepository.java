package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.BlackListDO;
import team.opentech.usher.pojo.entity.BlackList;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 黑名单(BlackList)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
public interface BlackListRepository extends BaseEntityRepository<BlackListDO, BlackList> {


    /**
     * 获取所有的ip黑名单
     *
     * @return
     */
    List<String> findAllIpBlackList();

}