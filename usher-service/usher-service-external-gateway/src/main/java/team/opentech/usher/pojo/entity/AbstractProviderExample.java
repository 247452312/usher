package team.opentech.usher.pojo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.DO.base.BaseDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月08日 15时06分
 */
public abstract class AbstractProviderExample<T extends BaseDO> extends AbstractDoEntity<T> implements ProviderExample {

    /**
     * 对应主表信息
     */
    protected ProviderInterface providerInterface;

    protected AbstractProviderExample(T t) {
        super(t);
    }

    protected AbstractProviderExample() {
    }

    protected AbstractProviderExample(Long id, T t) {
        super(id, t);
    }


    @Override
    public NodeInvokeResult invoke(Map<String, String> header, Map<String, Object> params, List<ProviderInterfaceParam> shouldParams) {
        // providerInterface改造为可以使用的
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(null);
        nodeInvokeResult.setFieldInfos(new ArrayList<>());
        nodeInvokeResult.setResult(new ArrayList<>());
        return nodeInvokeResult;
    }

    @Override
    public void fillInterface(ProviderInterface providerInterface) {
        this.providerInterface = providerInterface;
    }


}
