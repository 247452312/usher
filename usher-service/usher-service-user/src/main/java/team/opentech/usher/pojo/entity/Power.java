package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.PowerDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.PowerRepository;
import team.opentech.usher.util.Asserts;
import java.util.Optional;


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

    public Power(Identifier powerId) {
        super(powerId, new PowerDO());
    }


    public Power(Long id) {
        super(id, new PowerDO());
    }

    public void removeSelfLink(PowerRepository rep) {
        rep.removeDeptPowerByPowerId(this);
    }

    public void removeSelf(PowerRepository rep) {
        Optional<Identifier> unique = this.getUnique();
        Asserts.assertTrue(unique.isPresent(), "唯一标识");
        rep.remove(unique.get());
    }

}
