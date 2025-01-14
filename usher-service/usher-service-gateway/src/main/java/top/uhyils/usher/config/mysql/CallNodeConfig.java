package top.uhyils.usher.config.mysql;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import top.uhyils.usher.enums.DefaultSupportTypeEnum;
import top.uhyils.usher.handler.NodeHandler;
import top.uhyils.usher.node.NodeFactory;
import top.uhyils.usher.node.call.CallNode;
import top.uhyils.usher.node.call.SqlCallNode;
import top.uhyils.usher.pojo.TableInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月13日 19时47分
 */
@Component
public class CallNodeConfig {

    @Resource
    private NodeHandler nodeHandler;


    @PostConstruct
    public void init() {
        NodeFactory.addSupportType(DefaultSupportTypeEnum.SQL.getType(), (invokeCommand, tableInfo) -> {
            TableInfo info = nodeHandler.findInfo(invokeCommand);
            return new SqlCallNode(invokeCommand, info, sqlInvokeCommand -> {
                CallNode callNode = nodeHandler.makeNode(sqlInvokeCommand);
                return callNode.call(sqlInvokeCommand.getHeader(), sqlInvokeCommand.getParams());
            });
        });
    }

}
