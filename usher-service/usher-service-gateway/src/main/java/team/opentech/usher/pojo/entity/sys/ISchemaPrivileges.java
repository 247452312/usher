package team.opentech.usher.pojo.entity.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import team.opentech.usher.assembler.CompanyAssembler;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.IUserPrivilegesInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.cqe.UserQuery;
import team.opentech.usher.service.GatewaySdkService;
import team.opentech.usher.util.SpringUtil;

/**
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 13时51分
 */
public class ISchemaPrivileges extends AbstractSysTable {

    private final GatewaySdkService service;

    private final CompanyAssembler companyAssembler;

    public ISchemaPrivileges(Map<String, Object> params) {
        super(params);
        this.service = SpringUtil.getBean(GatewaySdkService.class);
        this.companyAssembler = SpringUtil.getBean(CompanyAssembler.class);
    }

    @Override
    protected NodeInvokeResult doGetResultNoParams() {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        /*数据从company里取,*/
        List<CompanyDTO> users = service.queryUser(new UserQuery());

        List<IUserPrivilegesInfo> userInfos = new ArrayList<>();
        for (CompanyDTO user : users) {
            IUserPrivilegesInfo mUserInfo = companyAssembler.toIUserPrivileges(user);
            userInfos.add(mUserInfo);
        }

        fieldInfos.add(new FieldInfo("information_schema", "user_privileges", "user_privileges", "GRANTEE", "GRANTEE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "user_privileges", "user_privileges", "TABLE_CATALOG", "TABLE_CATALOG", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "user_privileges", "user_privileges", "PRIVILEGE_TYPE", "PRIVILEGE_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "user_privileges", "user_privileges", "IS_GRANTABLE", "IS_GRANTABLE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        JSONArray objects = JSON.parseArray(JSON.toJSONString(userInfos));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            result.add(jsonObject);
        }

        return NodeInvokeResult.build(fieldInfos, result, null);
    }
}
