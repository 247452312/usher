package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OutApiDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.OutApiRepository;

/**
 * 开放api(OutApi)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月09日 20时58分10秒
 */
public class OutApi extends AbstractDoEntity<OutApiDO> {

    @Default
    public OutApi(OutApiDO data) {
        super(data);
    }

    public OutApi(Long id) {
        super(id, new OutApiDO());
    }

    public OutApi(Long id, OutApiRepository rep) {
        super(id, new OutApiDO());
        completion(rep);
    }

    public OutApi(Identifier id) {
        super(id, new OutApiDO());
    }

}
