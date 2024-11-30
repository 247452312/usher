package top.uhyils.usher.pojo.entity;

import java.util.Optional;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.PowerDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.PowerRepository;
import top.uhyils.usher.util.Asserts;


/**
 * 权限
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 08时14分
 */
public class Power extends AbstractDoEntity<PowerDO> {

    @Default
    public Power(PowerDO data) {
        super(data);
    }


    public Power(Long id) {
        super(id, new PowerDO());
    }

    public void removeSelfLink(PowerRepository rep) {
        rep.removeDeptPowerByPowerId(this);
    }

    public void removeSelf(PowerRepository rep) {
        Optional<Long> unique = this.getUnique();
        Asserts.assertTrue(unique.isPresent(), "唯一标识");
        rep.remove(unique.get());
    }

}
