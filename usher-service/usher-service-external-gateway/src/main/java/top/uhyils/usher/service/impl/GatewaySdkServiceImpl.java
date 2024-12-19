package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.assembler.CallNodeAssembler;
import top.uhyils.usher.assembler.CompanyAssembler;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.enums.InvokeTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.pojo.DTO.CallNodeDTO;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.pojo.cqe.InvokeCommand;
import top.uhyils.usher.pojo.cqe.UserQuery;
import top.uhyils.usher.pojo.entity.CallNode;
import top.uhyils.usher.pojo.entity.Company;
import top.uhyils.usher.pojo.entity.ProviderInterface;
import top.uhyils.usher.pojo.entity.SysProviderInterface;
import top.uhyils.usher.repository.CallNodeRepository;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.service.GatewaySdkService;
import top.uhyils.usher.util.GatewayUtil;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时23分
 */
@Service
public class GatewaySdkServiceImpl implements GatewaySdkService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyAssembler companyAssembler;

    @Autowired
    private ProviderInterfaceRepository providerInterfaceRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private CallNodeRepository callNodeRepository;

    @Resource
    private CallNodeAssembler callNodeAssembler;

    @Override
    @Public
    public NodeInvokeResult invokeInterface(InvokeCommand command) {

        Pair<String, String> splitDataBaseUrl = GatewayUtil.splitDataBaseUrl(command.getPath());
        ProviderInterface providerInterface = providerInterfaceRepository.find(command.getInvokeType(), splitDataBaseUrl.getKey(), splitDataBaseUrl.getValue());
        providerInterface.fill(nodeRepository, providerInterfaceRepository);
        //        JSONArray.parseArray(JSONObject.toJSONString(Arrays.asList(command)))
        NodeInvokeResult result = providerInterface.getResult(command.getHeader(), command.getParams());
        return new NodeInvokeResult(null);
    }

    @NotNull
    @Override
    public NodeInvokeResult invokeCallNode(InvokeCommand command) {
        String path = command.getPath();
        Boolean isSysTable = callNodeRepository.judgeSysTable(path);
        if (isSysTable) {
            // 系统表
            SysProviderInterface providerInterface = new SysProviderInterface(command.getPath());
            return providerInterface.getResult(command.getHeader(), command.getParams());
        } else {
            Pair<String, String> splitDataBaseUrl = GatewayUtil.splitDataBaseUrl(path);
            CallNode node = callNodeRepository.findNodeByDatabaseAndTable(splitDataBaseUrl.getKey(), splitDataBaseUrl.getValue(), InvokeTypeEnum.parse(command.getInvokeType()));
            node.fill(nodeRepository, providerInterfaceRepository);
            return node.getResult(command.getHeader(), command.getParams());
        }

    }

    @Override
    public List<CompanyDTO> queryUser(UserQuery userQuery) {
        List<Company> result = companyRepository.queryUser(userQuery.getUsername());
        return companyAssembler.listEntityToDTO(result);
    }

    @Override
    public List<CallNodeDTO> queryCallNode(CallNodeQuery callNodeQuery) {
        UserDTO userDTO = LoginInfoHelper.doGet();
        List<CallNode> callNodes = callNodeRepository.query(userDTO.getId(), callNodeQuery);
        return callNodeAssembler.listEntityToDTO(callNodes);
    }


}
