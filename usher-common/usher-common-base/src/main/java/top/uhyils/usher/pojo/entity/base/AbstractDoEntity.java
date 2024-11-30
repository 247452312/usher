package top.uhyils.usher.pojo.entity.base;

import java.util.Optional;
import top.uhyils.usher.pojo.DO.base.BaseDO;
import top.uhyils.usher.repository.base.BaseEntityRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.BeanUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时59分
 */
public abstract class AbstractDoEntity<T extends BaseDO> extends AbstractEntity<Long> implements DoEntity<T> {

    /**
     * 对应数据库DO
     */
    protected T data;

    /**
     * 数据库是否被修改
     */
    private boolean canUpdate;

    protected AbstractDoEntity(T t) {
        super(t.getId());
        this.data = t;
        this.canUpdate = false;
    }

    protected AbstractDoEntity() {
        super();
    }

    protected AbstractDoEntity(Long id, T t) {
        super(id);
        this.data = t;
        this.data.setId(id);
    }


    public <DO extends T, EN extends AbstractDoEntity<DO>> void completion(BaseEntityRepository<DO, EN> repository) {
        Asserts.assertTrue(this.unique != null, "数据库id不存在 不能补全");
        AbstractDoEntity<DO> dictItem = repository.find(this);
        Asserts.assertTrue(dictItem != null, "补全出的结果为空");
        Optional<DO> dataOptional = dictItem.toData();
        dataOptional.ifPresent(t -> {
            this.data = t;
            setUnique(t.getId());
        });
    }

    /**
     * 转换为DO
     *
     * @return
     */
    @Override
    public Optional<T> toData() {
        return Optional.of(data);
    }

    /**
     * 转换为DO
     *
     * @return
     */
    @Override
    public T toDataAndValidate() {
        Asserts.assertTrue(data != null, "未初始化");
        return data;
    }

    /**
     * 升级do中的id为id
     */
    public void upId() {
        T dataAndValidate = toDataAndValidate();
        this.unique = dataAndValidate.getId();
    }

    /**
     * 清空所有id
     */
    public void removeId() {
        T dataAndValidate = toDataAndValidate();
        dataAndValidate.setId(null);
        this.unique = null;
    }

    public <EN extends AbstractDoEntity<T>> void saveSelf(BaseEntityRepository<T, EN> repository) {
        repository.save((EN) this);
    }


    public <EN extends AbstractDoEntity<T>> void removeSelf(BaseEntityRepository<T, EN> repository) {
        repository.remove((EN) this);
    }


    public boolean canUpdate() {
        return canUpdate;
    }

    public void onUpdate() {
        this.canUpdate = true;
    }

    public void perUpdate() {
        data.preUpdate();
    }

    public void perInsert() {
        data.preInsert();
    }

    @Override
    public boolean haveId() {
        return unique != null && unique != null && unique > 0;
    }

    @Override
    public boolean notHaveId() {
        return !haveId();
    }

    /**
     * 从另一个entity中复制来
     *
     * @param entity
     */
    protected void copyOf(DoEntity<T> entity) {
        Optional<T> target = entity.toData();
        target.ifPresent(t -> BeanUtil.copyProperties(t, this.data));
        Optional<Long> unique = entity.getUnique();
        unique.ifPresent(t -> this.unique = t);
    }


}
