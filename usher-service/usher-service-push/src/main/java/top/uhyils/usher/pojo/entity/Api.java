package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.ApiDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.ApiRepository;

/**
 * api表(Api)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 19时46分45秒
 */
public class Api extends AbstractDoEntity<ApiDO> {


    @Default
    public Api(ApiDO data) {
        super(data);
    }

    public Api(Long id) {
        super(id, new ApiDO());
    }

    public Api(Long id, ApiRepository rep) {
        super(id, new ApiDO());
        completion(rep);
    }


}
