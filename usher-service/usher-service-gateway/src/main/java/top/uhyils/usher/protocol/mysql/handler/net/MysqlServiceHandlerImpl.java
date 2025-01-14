package top.uhyils.usher.protocol.mysql.handler.net;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.assembler.GatewayAssembler;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.content.CallNodeContent;
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
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.node.DatabaseInfo;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.SqlGlobalVariables;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.service.CompanyService;
import top.uhyils.usher.service.NetNodeInfoService;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 18时01分
 */
@Service
public class MysqlServiceHandlerImpl implements MysqlServiceHandler {

    @Resource
    private NetNodeInfoService netNodeInfoService;

    @Resource
    private CompanyService companyService;


    @Resource
    private GatewayAssembler gatewayAssembler;

    @Resource
    private NetNodeInfoAssembler nodeInfoAssembler;

    @Override
    public MysqlResponse mysqlLogin(MysqlAuthCommand command) {
        // 0.查询用户
        CompanyDTO companyDTO = companyService.findByAk(command.getUsername());

        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        // 1.判断密码是否正确
        if (MysqlUtil.checkSkByMysqlChallenge(companyDTO.getSk(), mysqlTcpLink.randomByte(), command.getChallenge())) {
            // 2.登录成功  注意: 登录成功后,需要将用户信息存入tcp连接的缓存中,不需要token,和redis
            String ip = mysqlTcpLink.findLocalAddress().getAddress().getHostName();
            UserDTO userDTO = gatewayAssembler.toUserDTO(ip, companyDTO);
            LoginInfoHelper.setIp(ip);
            LoginInfoHelper.setUser(userDTO);
            CallNodeContent.CALLER_INFO.get().setUserDTO(userDTO);
            return new OkResponse(SqlTypeEnum.NULL);
        }
        return ErrResponse.build("密码错误,密码请使用secretKey");
    }

    @Override
    public List<DatabaseInfo> getAllDatabaseInfo(BlackQuery blackQuery) {
        UserDTO userDTO = LoginInfoHelper.get().orElseThrow(() -> Asserts.makeException("未登录"));
        Asserts.assertTrue(userDTO != null, "未登录");
        List<NetNodeInfoDTO> callNodes = netNodeInfoService.findWithDetailByCompanyId(IdCommand.build(userDTO.getId()));
        return callNodes.ustream().distinct(NetNodeInfoDTO::getDatabase).map(t -> toDatabaseInfo(t.getDatabase())).filter(Objects::nonNull).toList();
    }


    @Override
    public List<CompanyInfo> queryUser(UserQuery userQuery) {
        List<CompanyDTO> companyDTOS = companyService.queryCompany(gatewayAssembler.toUserQuery(userQuery));
        return gatewayAssembler.toCompanyInfo(companyDTOS);
    }

    @Override
    public List<TableDTO> queryTable(TableQuery tableQuery) {
        UserDTO userDTO = LoginInfoHelper.get().orElseThrow(() -> Asserts.makeException("未登录"));
        Asserts.assertTrue(userDTO != null, "未登录");
        List<NetNodeInfoDTO> callNodes = netNodeInfoService.findWithDetailByCompanyId(IdCommand.build(userDTO.getId()));
        return nodeInfoAssembler.toTableDTO(userDTO.getId(), callNodes);
    }

    @Override
    public SqlGlobalVariables findMysqlGlobalVariables() {
        return new SqlGlobalVariables();
    }

    @Override
    public List<TableDTO> findTableByCompanyAndDatabase(Long companyId, List<String> databases) {
        return nodeInfoAssembler.toTableDTO(companyId, netNodeInfoService.findByCompanyIdAndDatabase(companyId, databases));
    }

    private DatabaseInfo toDatabaseInfo(String database) {
        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setCatalogName(CallNodeContent.CATALOG_NAME);
        databaseInfo.setSchemaName(database);
        databaseInfo.setDefaultCharacterSetName(CallNodeContent.DEFAULT_CHARACTER_SET_NAME);
        databaseInfo.setDefaultCollationName(CallNodeContent.DEFAULT_COLLATION_NAME);
        databaseInfo.setSqlPath(null);
        databaseInfo.setDefaultEncryption("NO");
        return databaseInfo;
    }

}
