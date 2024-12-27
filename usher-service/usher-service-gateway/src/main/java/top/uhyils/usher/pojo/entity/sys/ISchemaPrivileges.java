package top.uhyils.usher.pojo.entity.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.assembler.CallNodeAssembler;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.ISchemaPrivilegesInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.pojo.DTO.CallNodeDTO;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.service.GatewaySdkService;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 13时51分
 */
public class ISchemaPrivileges extends AbstractSysTable {

    private final GatewaySdkService service;


    private final CallNodeAssembler callNodeAssembler;

    public ISchemaPrivileges(Map<String, Object> params) {
        super(params);
        this.service = SpringUtil.getBean(GatewaySdkService.class);
        this.callNodeAssembler = SpringUtil.getBean(CallNodeAssembler.class);
    }

    @Override
    protected NodeInvokeResult doGetResultNoParams() {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        /*数据从company里取,*/
        List<CallNodeDTO> callNodeDTOS = service.queryCallNode(new CallNodeQuery());

        List<ISchemaPrivilegesInfo> userInfos = new ArrayList<>();
        for (CallNodeDTO user : callNodeDTOS) {
            ISchemaPrivilegesInfo mUserInfo = callNodeAssembler.toISchemaPrivilegesInfo(user);
            userInfos.add(mUserInfo);
        }

        fieldInfos.add(new FieldInfo("information_schema", "schema_privileges", "schema_privileges", "GRANTEE", "GRANTEE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "schema_privileges", "schema_privileges", "TABLE_CATALOG", "TABLE_CATALOG", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "schema_privileges", "schema_privileges", "TABLE_SCHEMA", "TABLE_SCHEMA", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "schema_privileges", "schema_privileges", "PRIVILEGE_TYPE", "PRIVILEGE_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "schema_privileges", "schema_privileges", "IS_GRANTABLE", "IS_GRANTABLE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        JSONArray objects = JSON.parseArray(JSON.toJSONString(userInfos));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            result.add(jsonObject);
        }

        return NodeInvokeResult.build(fieldInfos, result, null);
    }
}
