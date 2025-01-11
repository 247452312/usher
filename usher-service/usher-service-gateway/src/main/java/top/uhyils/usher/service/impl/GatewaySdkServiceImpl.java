package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.assembler.CompanyAssembler;
import top.uhyils.usher.assembler.GatewayAssembler;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.pojo.sys.SysProviderInterface;
import top.uhyils.usher.node.call.CallNode;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.NodeInvokeResult;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.pojo.cqe.UserQuery;
import top.uhyils.usher.pojo.entity.Company;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.service.GatewaySdkService;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时23分
 */
@Service
public class GatewaySdkServiceImpl implements GatewaySdkService {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private GatewayAssembler gatewayAssembler;

    @Resource
    private CompanyAssembler companyAssembler;

    @Resource
    private NetNodeInfoRepository netNodeInfoRepository;

    @Resource
    private NetNodeInfoAssembler netNodeInfoAssembler;


    @NotNull
    @Override
    public NodeInvokeResult invokeCallNode(SqlInvokeCommand command) {
        Boolean isSysTable = netNodeInfoRepository.judgeSysTable(command.getDatabase());
        if (isSysTable) {
            // 系统表
            SysProviderInterface providerInterface = new SysProviderInterface(command.getDatabase(), command.getTable(), SpringUtil.getBean(MysqlServiceHandler.class));
            return providerInterface.getResult(command.getHeader(), command.getParams());
        } else {
            NetNodeInfo node = netNodeInfoRepository.findNodeByDatabaseAndTable(command.getDatabase(), command.getTable());
            gatewayAssembler.toInvoke(new SqlInvokeCommand());
            CallNode callNode = node.toCallNode(command);
            return callNode.call(command.getHeader(), command.getParams());
        }
    }


    @Override
    public List<CompanyDTO> queryUser(UserQuery userQuery) {
        List<Company> result = companyRepository.queryUser(userQuery.getUsername());
        return companyAssembler.listEntityToDTO(result);
    }

    @Override
    public List<NetNodeInfoDTO> queryCallNode(CallNodeQuery callNodeQuery) {
        UserDTO userDTO = LoginInfoHelper.doGet();
        List<NetNodeInfo> callNodes = netNodeInfoRepository.query(userDTO.getId(), callNodeQuery);
        return netNodeInfoAssembler.listEntityToDTO(callNodes);
    }


}
