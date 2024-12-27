package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.BlackListDO;
import top.uhyils.usher.pojo.entity.BlackList;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
