package team.opentech.usher.pojo.entity;

import java.util.Map;
import java.util.Optional;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.DTO.DatabaseInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.DO.CallNodeDO;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.GatewayUtil;
import team.opentech.usher.util.Pair;

/**
 * 调用节点表, 真正调用的节点(CallNode)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class CallNode extends AbstractDataNode<CallNodeDO> {

    /**
     * 中间节点
     */
    private Node node;

    @Default
    public CallNode(CallNodeDO data) {
        super(data);
    }

    public CallNode(Long id) {
        super(id, new CallNodeDO());
    }

    @NotNull
    @Override
    public String databaseName() {
        CallNodeDO node = toData().orElseThrow(() -> Asserts.makeException("未填充内容"));
        String url = node.getUrl();
        int firstIndex = url.indexOf("/");
        return url.substring(0, firstIndex);
    }

    @NotNull
    @Override
    public String tableName() {
        CallNodeDO node = toData().orElseThrow(() -> Asserts.makeException("未填充内容"));
        String url = node.getUrl();
        int firstIndex = url.indexOf("/");
        return url.substring(firstIndex + 1);
    }

    /**
     * 转换为数据库格式
     *
     * @return
     */
    public DatabaseInfo changeToDatabaseInfo() {
        CallNodeDO callNodeDO = toData().orElseThrow(() -> Asserts.makeException("未填充"));
        String url = callNodeDO.getUrl();
        Pair<String, String> databaseAndTable = GatewayUtil.splitDataBaseUrl(url);
        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setCatalogName(MysqlContent.CATALOG_NAME);
        String key = databaseAndTable.getKey();
        if (key == null) {
            return null;
        }
        databaseInfo.setSchemaName(key);
        databaseInfo.setDefaultCharacterSetName(MysqlContent.DEFAULT_CHARACTER_SET_NAME);
        databaseInfo.setDefaultCollationName(MysqlContent.DEFAULT_COLLATION_NAME);
        databaseInfo.setSqlPath(null);
        databaseInfo.setDefaultEncryption("NO");
        return databaseInfo;
    }

    /**
     * 向下填充
     *
     * @param nodeRepository
     */
    @Override
    public void fill(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository) {
        Optional<CallNodeDO> callNodeOptional = toData();
        callNodeOptional.ifPresent(callNodeDO -> {
            this.node = nodeRepository.find(new CallNode(callNodeDO.getNodeId()));
            this.node.fill(nodeRepository, providerInterfaceRepository);
        });
    }

    @Override
    public NodeInvokeResult getResult(Map<String, String> header, Map<String, Object> params) {
        Asserts.assertTrue(node != null, "callNode未初始化");
        return node.getResult(header, params);
    }
}
