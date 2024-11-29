package team.opentech.usher.pojo.entity.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;

/**
 * 排序规则
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月02日 10时39分
 */
public class ICollatinos extends AbstractSysTable {

    public ICollatinos(Map<String, Object> params) {
        super(params);
    }

    @Override
    protected NodeInvokeResult doGetResultNoParams() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(null);

        List<Map<String, Object>> result = new ArrayList<>();
        nodeInvokeResult.setResult(result);
        List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "COLLATION_NAME", "COLLATION_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "CHARACTER_SET_NAME", "CHARACTER_SET_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "ID", "ID", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "IS_DEFAULT", "IS_DEFAULT", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "IS_COMPILED", "IS_COMPILED", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "collatinos", "collatinos", "SORTLEN", "SORTLEN", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        nodeInvokeResult.setFieldInfos(fieldInfos);
        return nodeInvokeResult;
    }
}
