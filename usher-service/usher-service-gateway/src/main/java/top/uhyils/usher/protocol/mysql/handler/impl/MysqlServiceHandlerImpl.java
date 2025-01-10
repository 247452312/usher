package top.uhyils.usher.protocol.mysql.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.assembler.GatewayAssembler;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.SqlTypeEnum;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.pojo.DTO.CompanyInfo;
import top.uhyils.usher.mysql.pojo.DTO.TableDTO;
import top.uhyils.usher.mysql.pojo.cqe.TableQuery;
import top.uhyils.usher.mysql.pojo.cqe.UserQuery;
import top.uhyils.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.ErrResponse;
import top.uhyils.usher.mysql.pojo.response.impl.OkResponse;
import top.uhyils.usher.node.DatabaseInfo;
import top.uhyils.usher.node.call.CallNode;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.NodeInvokeResult;
import top.uhyils.usher.pojo.SqlGlobalVariables;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.entity.Company;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.service.GatewaySdkService;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 18时01分
 */
@Service
public class MysqlServiceHandlerImpl implements MysqlServiceHandler {

    @Resource
    private GatewaySdkService gatewaySdkService;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private NetNodeInfoRepository netNodeInfoRepository;

    @Resource
    private NetNodeInfoAssembler nodeInfoAssembler;

    @Resource
    private GatewayAssembler gatewayAssembler;

    @Override
    public MysqlResponse mysqlLogin(MysqlAuthCommand command) {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        Company company = new Company(command);

        // 0.查询用户
        company.completionByAk(companyRepository);

        // 1.判断密码是否正确
        if (company.checkSkByMysqlChallenge(mysqlTcpLink.randomByte(), command.getChallenge())) {
            UserDTO userDTO = company.mysqlLogin();
            mysqlTcpLink.fillUserDTO(userDTO);
            return new OkResponse(SqlTypeEnum.NULL);
        }
        return ErrResponse.build("密码错误,密码请使用secretKey");
    }

    @Override
    public List<DatabaseInfo> getAllDatabaseInfo(BlackQuery blackQuery) {
        UserDTO userDTO = LoginInfoHelper.get().orElseThrow(() -> Asserts.makeException("未登录"));
        Asserts.assertTrue(userDTO != null, "未登录");
        List<NetNodeInfo> callNodes = netNodeInfoRepository.findByUser(userDTO);
        return new ArrayList<>(callNodes.stream()
                                        .map(t -> t.toCallNode(null))
                                        .map(CallNode::changeToDatabaseInfo)
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toMap(DatabaseInfo::getSchemaName, t -> t, (key1, key2) -> key2))
                                        .values());
    }


    @Override
    public NodeInvokeResult invokeCallNode(SqlInvokeCommand command) {
        SqlInvokeCommand invokeCommand = gatewayAssembler.toInvoke(command);
        return gatewaySdkService.invokeCallNode(invokeCommand);
    }

    @Override
    public NodeInvokeResult invokeSingleQuerySql(SqlInvokeCommand command) {
        SqlInvokeCommand invokeCommand = gatewayAssembler.toInvoke(command);
        return gatewaySdkService.invokeCallNode(invokeCommand);
    }

    @Override
    public List<CompanyInfo> queryUser(UserQuery userQuery) {
        List<CompanyDTO> companyDTOS = gatewaySdkService.queryUser(gatewayAssembler.toUserQuery(userQuery));
        return gatewayAssembler.toUserDTO(companyDTOS);
    }

    @Override
    public List<TableDTO> queryTable(TableQuery tableQuery) {
        List<NetNodeInfoDTO> callNodeDTOS = gatewaySdkService.queryCallNode(gatewayAssembler.toCallNode(tableQuery));
        return gatewayAssembler.toTableDTO(callNodeDTOS);
    }

    @Override
    public SqlGlobalVariables findMysqlGlobalVariables() {
        return new SqlGlobalVariables();
    }

    @Override
    public List<TableDTO> findTableByCompanyAndDatabase(Long companyId, String database) {
        List<NetNodeInfo> nodes = netNodeInfoRepository.findByCompanyIdAndDatabase(companyId, database);
        List<NetNodeInfoDTO> netNodeInfoDTOS = nodeInfoAssembler.listEntityToDTO(nodes);
        return gatewayAssembler.toTableDTO(netNodeInfoDTOS);
    }
}
