package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.annotation.Public;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.pojo.DTO.DatabaseInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.ErrResponse;
import team.opentech.usher.mysql.pojo.response.impl.OkResponse;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.pojo.entity.CallNode;
import team.opentech.usher.pojo.entity.Company;
import team.opentech.usher.pojo.entity.ProviderInterface;
import team.opentech.usher.pojo.entity.SysProviderInterface;
import team.opentech.usher.repository.CallNodeRepository;
import team.opentech.usher.repository.CompanyRepository;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.service.GatewaySdkService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.GatewayUtil;
import team.opentech.usher.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时23分
 */
@Service
public class GatewaySdkServiceImpl implements GatewaySdkService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProviderInterfaceRepository providerInterfaceRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private CallNodeRepository callNodeRepository;

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
    public MysqlResponse mysqlLogin(MysqlAuthCommand command) {
        MysqlTcpInfo mysqlTcpInfo = MysqlContent.MYSQL_TCP_INFO.get();
        Company company = new Company(command);

        // 0.查询用户
        company.completionByAk(companyRepository);

        // 1.判断密码是否正确
        if (company.checkSkByMysqlChallenge(mysqlTcpInfo.getRandomByte(), command.getChallenge())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setIp(UserInfoHelper.getUserIp().orElse(null));
            userDTO.setId(company.unique.getId());
            mysqlTcpInfo.setUserDTO(userDTO);
            return new OkResponse(SqlTypeEnum.NULL);
        }
        return ErrResponse.build("密码错误,密码请使用secretKey");
    }

    @Override
    public List<DatabaseInfo> getAllDatabaseInfo(BlackQuery blackQuery) {
        UserDTO userDTO = UserInfoHelper.get().orElseThrow(() -> Asserts.makeException("未登录"));
        Asserts.assertTrue(userDTO != null, "未登录");
        List<CallNode> callNodes = callNodeRepository.findByUser(userDTO);
        return new ArrayList<>(callNodes.stream()
                                        .map(CallNode::changeToDatabaseInfo)
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toMap(DatabaseInfo::getSchemaName, t -> t, (key1, key2) -> key2))
                                        .values());
    }

}
