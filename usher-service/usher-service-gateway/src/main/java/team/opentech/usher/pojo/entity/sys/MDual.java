package team.opentech.usher.pojo.entity.sys;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月11日 16时15分
 */
public class MDual extends AbstractSysTable {

    public MDual(Map<String, Object> params) {
        super(params);
        this.params = params.entrySet().stream().collect(Collectors.toMap(t -> t.getKey().toLowerCase(), Entry::getValue));
    }

    @Override
    public NodeInvokeResult doGetResultNoParams() {
        return NodeInvokeResult.build(new ArrayList<>(), new ArrayList<>(), null);
    }
}
