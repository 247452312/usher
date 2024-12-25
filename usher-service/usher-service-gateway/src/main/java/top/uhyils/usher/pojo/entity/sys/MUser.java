package top.uhyils.usher.pojo.entity.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.assembler.CompanyAssembler;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.MUserInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.cqe.UserQuery;
import top.uhyils.usher.service.GatewaySdkService;
import top.uhyils.usher.util.SpringUtil;

/**
 * mysql.user表
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 10时54分
 */
public class MUser extends AbstractSysTable {

    private final GatewaySdkService service;

    private final CompanyAssembler companyAssembler;

    public MUser(Map<String, Object> params) {
        super(params);
        this.service = SpringUtil.getBean(GatewaySdkService.class);
        this.companyAssembler = SpringUtil.getBean(CompanyAssembler.class);
    }

    @Override
    protected NodeInvokeResult doGetResultNoParams() {
        /*数据从company里取,*/
        List<CompanyDTO> users = service.queryUser(new UserQuery());

        List<MUserInfo> userInfos = new ArrayList<>();
        for (CompanyDTO user : users) {
            MUserInfo mUserInfo = companyAssembler.toMUser(user);
            userInfos.add(mUserInfo);
        }
        List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "HOST", "HOST", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "USER", "USER", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SELECT_PRIV", "SELECT_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "INSERT_PRIV", "INSERT_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "UPDATE_PRIV", "UPDATE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "DELETE_PRIV", "DELETE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_PRIV", "CREATE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "DROP_PIV", "DROP_PIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "RELOAD_PRIV", "RELOAD_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SHUTDOWN_PRIV", "SHUTDOWN_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PROCESS_PRIV", "PROCESS_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "FILE_PRIV", "FILE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "GRANT_PRIV", "GRANT_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "REFERENCES_PRIV", "REFERENCES_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "INDEX_PRIV", "INDEX_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "ALTER_PRIV", "ALTER_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SHOW_DB_PRIV", "SHOW_DB_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SUPER_PRIV", "SUPER_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_TMP_TABLE_PRIV", "CREATE_TMP_TABLE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "LOCK_TABLES_PRIV", "LOCK_TABLES_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "EXECUTE_PRIV", "EXECUTE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "REPL_SLAVE_PRIV", "REPL_SLAVE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "REPL_CLIENT_PRIV", "REPL_CLIENT_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_VIEW_PRIV", "CREATE_VIEW_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SHOW_VIEW_PRIV", "SHOW_VIEW_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_ROUTINE_PRIV", "CREATE_ROUTINE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "ALTER_ROUTINE_PRIV", "ALTER_ROUTINE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_USER_PRIV", "CREATE_USER_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "EVENT_PRIV", "EVENT_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "TRIGGER_PRIV", "TRIGGER_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_TABLESPACE_PRIV", "CREATE_TABLESPACE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SSL_TYPE", "SSL_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "SSL_CIPHER", "SSL_CIPHER", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "X509_ISSUER", "X509_ISSUER", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "X509_SUBJECT", "X509_SUBJECT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "MAX_QUESTIONS", "MAX_QUESTIONS", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "MAX_UPDATES", "MAX_UPDATES", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "MAX_CONNECTIONS", "MAX_CONNECTIONS", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "MAX_USER_CONNECTIONS", "MAX_USER_CONNECTIONS", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PLUGIN", "PLUGIN", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "AUTHENTICATION_STRING", "AUTHENTICATION_STRING", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_EXPIRED", "PASSWORD_EXPIRED", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_LAST_CHANGED", "PASSWORD_LAST_CHANGED", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_LIFETIME", "PASSWORD_LIFETIME", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "ACCOUNT_LOCKED", "ACCOUNT_LOCKED", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "CREATE_ROLE_PRIV", "CREATE_ROLE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "DROP_ROLE_PRIV", "DROP_ROLE_PRIV", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_REUSE_HISTORY", "PASSWORD_REUSE_HISTORY", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_REUSE_TIME", "PASSWORD_REUSE_TIME", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "PASSWORD_REQUIRE_CURRENT", "PASSWORD_REQUIRE_CURRENT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("mysql", "user", "user", "USER_ATTRIBUTES", "USER_ATTRIBUTES", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));

        JSONArray objects = JSON.parseArray(JSON.toJSONString(userInfos));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            result.add(jsonObject);
        }
        return NodeInvokeResult.build(fieldInfos, result, null);
    }
}
