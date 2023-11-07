package team.opentech.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.annotation.Public;
import team.opentech.usher.assembler.CallNodeAssembler;
import team.opentech.usher.assembler.CompanyAssembler;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.CallNodeQuery;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.UserQuery;
import team.opentech.usher.pojo.entity.CallNode;
import team.opentech.usher.pojo.entity.Company;
import team.opentech.usher.pojo.entity.ProviderInterface;
import team.opentech.usher.pojo.entity.SysProviderInterface;
import team.opentech.usher.repository.CallNodeRepository;
import team.opentech.usher.repository.CompanyRepository;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.service.GatewaySdkService;
import team.opentech.usher.util.GatewayUtil;
import team.opentech.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时23分
 */
@Service
public class GatewaySdkServiceImpl implements GatewaySdkService {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private CompanyAssembler companyAssembler;

    @Resource
    private ProviderInterfaceRepository providerInterfaceRepository;

    @Resource
    private NodeRepository nodeRepository;

    @Resource
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
        UserDTO userDTO = UserInfoHelper.doGet();
        List<CallNode> callNodes = callNodeRepository.query(userDTO.getId(), callNodeQuery);
        return callNodeAssembler.listEntityToDTO(callNodes);
    }


}
