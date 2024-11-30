package top.uhyils.usher.pojo.entity;

import java.util.List;
import java.util.Map;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.enums.InvokeTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.pojo.DO.ProviderInterfaceDO;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.util.Asserts;

/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class ProviderInterface extends AbstractDataNode<ProviderInterfaceDO> {


    /**
     * 需要的参数
     */
    private List<ProviderInterfaceParam> shouldParams;

    /**
     * 可执行的实例
     */
    private ProviderExample example;

    @Default
    public ProviderInterface(ProviderInterfaceDO data) {
        super(data);
    }

    public ProviderInterface(Long id) {
        super(id, new ProviderInterfaceDO());
    }

    public ProviderInterface(boolean isSysDatabase) {
        super(new ProviderInterfaceDO());
        Asserts.assertTrue(isSysDatabase, "非系统数据库请不要使用此后遭方法");
    }


    @Override
    public void fill(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository) {
        ProviderInterfaceDO providerInterfaceDO = toDataAndValidate();
        this.shouldParams = providerInterfaceRepository.findParamByInterfaceId(providerInterfaceDO.getId());
        InvokeTypeEnum type = InvokeTypeEnum.getByCode(providerInterfaceDO.getInvokeType());
        this.example = providerInterfaceRepository.findExample(providerInterfaceDO.getId(), type);
        this.example.fillInterface(this);
    }


    @Override
    public NodeInvokeResult getResult(Map<String, String> header, Map<String, Object> params) {
        Asserts.assertTrue(example != null, "执行前请先填充,对应方法 fill");
        return example.invoke(header, params, shouldParams);
    }


    @Override
    public String databaseName() {
        ProviderInterfaceDO providerInterfaceDO = toData().orElseThrow(() -> Asserts.makeException("未填充内容"));
        return providerInterfaceDO.getDatabase();
    }

    @Override
    public String tableName() {
        ProviderInterfaceDO providerInterfaceDO = toData().orElseThrow(() -> Asserts.makeException("未填充内容"));
        return providerInterfaceDO.getTable();
    }

}
