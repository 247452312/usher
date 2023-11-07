package team.opentech.usher.service.impl;

import team.opentech.usher.BaseTest;
import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.service.RelegationService;
import team.opentech.usher.util.Asserts;
import org.junit.jupiter.api.Test;
import javax.annotation.Resource;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月28日 16时58分
 */
public class RelegationServiceImplTest extends BaseTest {

    @Resource
    private RelegationService service;

    @Test
    public void demotion() {
        Boolean success = service.demotion("team.opentech.usher.protocol.rpc.DictProvider", "getByCode");
        Asserts.assertTrue(success);
        RelegationDTO query = service.query(new Identifier(1712022700888686720L));
        Asserts.assertTrue(query.getDisable() == false);
        Boolean getByCode = service.recover("team.opentech.usher.protocol.rpc.DictProvider", "getByCode");
        Asserts.assertTrue(getByCode);
        query = service.query(new Identifier(1712022700888686720L));
        Asserts.assertTrue(query.getDisable());

    }

    @Test
    public void recover() {
    }
}
