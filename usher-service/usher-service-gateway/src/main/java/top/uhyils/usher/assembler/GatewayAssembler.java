package top.uhyils.usher.assembler;

import java.util.List;
import org.mapstruct.Mapper;
import top.uhyils.usher.mysql.pojo.DTO.CompanyInfo;
import top.uhyils.usher.mysql.pojo.DTO.TableDTO;
import top.uhyils.usher.mysql.pojo.cqe.TableQuery;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.pojo.cqe.UserQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 18时04分
 */

@Mapper(componentModel = "spring")
public abstract class GatewayAssembler implements BaseAssembler {

    public SqlInvokeCommand toInvoke(SqlInvokeCommand command) {
        SqlInvokeCommand invokeCommand = new SqlInvokeCommand();
        invokeCommand.copyOf(command);
        invokeCommand.setParams(command.getParams());
        invokeCommand.setHeader(command.getHeader());
        invokeCommand.setDatabase(command.getDatabase());
        invokeCommand.setTable(command.getTable());
        invokeCommand.setType(command.getType());
        return invokeCommand;
    }

    public UserQuery toUserQuery(top.uhyils.usher.mysql.pojo.cqe.UserQuery userQuery) {
        UserQuery result = new UserQuery();
        result.copyOf(userQuery);
        result.setUsername(userQuery.getUsername());
        return result;
    }

    public abstract List<CompanyInfo> toUserDTO(List<CompanyDTO> companyDTOS);

    public UserDTO toUserDTO(CompanyDTO companyDTO) {
        UserDTO result = new UserDTO();
        result.setNickName(companyDTO.getName());
        result.setUsername(companyDTO.getAk());
        result.setMail(null);
        result.setPhone(companyDTO.getPersonPhone());
        result.setHeadPortrait(null);
        result.setRoleId(null);
        result.setRole(null);
        result.setStatus(1);
        return result;
    }

    public CallNodeQuery toCallNode(TableQuery tableQuery) {
        CallNodeQuery invokeCommand = new CallNodeQuery();
        invokeCommand.copyOf(tableQuery);
        return invokeCommand;
    }

    public abstract List<TableDTO> toTableDTO(List<NetNodeInfoDTO> callNodeDTOS);

    public TableDTO toTableDTO(NetNodeInfoDTO callNodeDTOS) {
        TableDTO result = new TableDTO();
        result.setCompanyId(callNodeDTOS.getCompanyId());
        result.setNodeId(callNodeDTOS.getId());
        result.setDatabase(callNodeDTOS.getDatabase());
        result.setTable(callNodeDTOS.getTable());

        return result;
    }
}
