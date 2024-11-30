package top.uhyils.usher.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.BaseTest;
import top.uhyils.usher.pojo.DTO.RelegationDTO;
import top.uhyils.usher.service.RelegationService;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月28日 16时58分
 */
public class RelegationServiceImplTest extends BaseTest {

    @Autowired
    private RelegationService service;

    @Test
    public void demotion() {
        Boolean success = service.demotion("top.uhyils.usher.protocol.rpc.DictProvider", "getByCode");
        Asserts.assertTrue(success);
        RelegationDTO query = service.query(1712022700888686720L);
        Asserts.assertTrue(query.getDisable() == false);
        Boolean getByCode = service.recover("top.uhyils.usher.protocol.rpc.DictProvider", "getByCode");
        Asserts.assertTrue(getByCode);
        query = service.query(1712022700888686720L);
        Asserts.assertTrue(query.getDisable());

    }

    @Test
    public void recover() {
    }
}
