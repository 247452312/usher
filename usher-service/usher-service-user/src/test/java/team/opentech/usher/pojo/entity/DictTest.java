package team.opentech.usher.pojo.entity;

import team.opentech.usher.BaseTest;
import team.opentech.usher.enums.DictTypeEnum;
import team.opentech.usher.pojo.DO.DictDO;
import team.opentech.usher.pojo.DO.DictItemDO;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DictItemRepository;
import team.opentech.usher.repository.DictRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Resource;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月08日 21时01分
 */
public class DictTest extends BaseTest {

    @Resource
    private DictRepository dictRepository;

    @Resource
    private DictItemRepository dictItemRepository;

    private Long dictId;

    private Long dictItemId;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        DictDO dO = new DictDO();
        dO.setCode("testCode");
        dO.setDescription("testDesc");
        dO.setName("testName");
        dO.setType(DictTypeEnum.INTEGER.getCode());
        Dict dict = new Dict(dO);
        dictRepository.save(dict);
        this.dictId = dict.getUnique().map(Identifier::getId).orElse(null);

        DictItemDO item = new DictItemDO();
        item.setDescription("itemDesc");
        item.setDictId(dict.getUnique().map(Identifier::getId).orElse(null));
        item.setSortOrder(1);
        item.setText("test");
        item.setValue("1");
        item.setStatus(1);
        DictItem dictItem = new DictItem(item);
        dictItemRepository.save(dictItem);
        dictItemId = dictItem.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到字典项id"));
    }

    @Test
    public void findItem() {
        Dict dict = new Dict("testCode");
        List<DictItem> item = dict.findItemByCode(dictItemRepository);
        Asserts.assertTrue(CollectionUtil.isNotEmpty(item));
        Asserts.assertTrue(item.size() == 1);
        Asserts.assertTrue(item.get(0).getUnique().get().getId().equals(dictItemId));
    }

    @Test
    public void fillItem() {
        Dict dict = new Dict(dictId);
        dict.completion(dictRepository);
        dict.fillItem(dictItemRepository);
        List<DictItem> dictItems = dict.toItem();
        Asserts.assertTrue(CollectionUtil.isNotEmpty(dictItems));
        Asserts.assertTrue(dictItems.size() == 1);
        Asserts.assertTrue(dictItems.get(0).getUnique().get().getId().equals(dictItemId));
    }


    @Test
    public void cleanItem() {
        Dict dict = new Dict(dictId);
        dict.fillItem(dictItemRepository);
        dict.cleanItem(dictItemRepository);
        dict.cleanItem(dictItemRepository);
        Dict dict2 = new Dict(dictId);
        dict2.fillItem(dictItemRepository);
        List<DictItem> dictItems = dict2.toItem();
        Asserts.assertTrue(dictItems != null);
        Asserts.assertTrue(dictItems.size() == 0);

    }
}
