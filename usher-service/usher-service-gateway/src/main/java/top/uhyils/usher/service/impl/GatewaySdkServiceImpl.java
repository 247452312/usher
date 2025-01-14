package top.uhyils.usher.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.handler.NodeHandler;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.node.call.CallNode;
import top.uhyils.usher.pojo.NodeInvokeResult;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.service.GatewaySdkService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时23分
 */
@Service
public class GatewaySdkServiceImpl implements GatewaySdkService {


    @Resource
    private NodeHandler handler;

    @Resource
    private MysqlServiceHandler mysqlServiceHandler;

    @NotNull
    @Override
    public NodeInvokeResult invokeCallNode(SqlInvokeCommand command) {
        CallNode callNode = MysqlUtil.parseCallNode(command, mysqlServiceHandler, handler);
        return callNode.call(command.getHeader(), command.getParams());
    }


}
