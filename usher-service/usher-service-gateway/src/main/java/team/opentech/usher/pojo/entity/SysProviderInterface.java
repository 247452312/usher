package team.opentech.usher.pojo.entity;

import java.util.Map;
import team.opentech.usher.enums.SysTableEnum;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.entity.sys.SysTable;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.GatewayUtil;
import team.opentech.usher.util.Pair;
import team.opentech.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月01日 11时02分
 */
public class SysProviderInterface extends ProviderInterface {

    /**
     * 系统数据库名称
     */
    private final String database;

    /**
     * 表
     */
    private final String table;


    public SysProviderInterface(String path) {
        super(true);
        Pair<String, String> stringStringPair = GatewayUtil.splitDataBaseUrl(path);
        this.database = stringStringPair.getKey();
        this.table = stringStringPair.getValue();
    }

    @Override
    public NodeInvokeResult getResult(Map<String, String> header, Map<String, Object> params) {
        Asserts.assertTrue(StringUtil.isNotEmpty(database) && StringUtil.isNotEmpty(table), "数据库不存在或表不存在");
        SysTableEnum parse = SysTableEnum.parse(database, table);
        SysTable sysTable = parse.getSysTable(params);
        return sysTable.getResult();
    }
}
