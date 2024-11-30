package top.uhyils.usher.pojo.entity;

import java.util.List;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.DictDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.DictItemRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;

/**
 * 数据字典(Dict)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月27日 08时32分39秒
 */
public class Dict extends AbstractDoEntity<DictDO> {

    private List<DictItem> dictItems;

    @Default
    public Dict(DictDO data) {
        super(data);
    }

    public Dict(String code) {
        super(parseCodeToDO(code));
    }

    public Dict(Long id) {
        super(id, new DictDO());
    }

    private static DictDO parseCodeToDO(String code) {
        DictDO dictDO = new DictDO();
        dictDO.setCode(code);
        return dictDO;
    }

    public List<DictItem> findItemByCode(DictItemRepository dictItemRepository) {
        Asserts.assertTrue(data.getCode() != null, "code不能为空");
        return dictItemRepository.findItemByDictCode(data.getCode());
    }

    public void fillItem(DictItemRepository rep) {
        this.dictItems = rep.findItemByDictId(this);
    }

    public List<DictItem> toItem() {
        return dictItems;
    }

    public void cleanItem(DictItemRepository rep) {
        if (CollectionUtil.isEmpty(dictItems)) {
            return;
        }
        int remove = rep.remove(dictItems);
        this.dictItems = null;
    }

}
