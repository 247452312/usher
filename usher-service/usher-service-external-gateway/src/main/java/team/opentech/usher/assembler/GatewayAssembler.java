package team.opentech.usher.assembler;

import java.util.List;
import org.mapstruct.Mapper;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.DTO.TableDTO;
import team.opentech.usher.mysql.pojo.cqe.MysqlInvokeCommand;
import team.opentech.usher.mysql.pojo.cqe.TableQuery;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.CallNodeQuery;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.UserQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 18时04分
 */

@Mapper(componentModel = "spring")
public abstract class GatewayAssembler implements BaseAssembler {

    public InvokeCommand toInvoke(MysqlInvokeCommand command) {
        InvokeCommand invokeCommand = new InvokeCommand();
        invokeCommand.copyOf(command);
        invokeCommand.setParams(command.getParams());
        invokeCommand.setHeader(command.getHeader());
        String path = command.getDatabase() + MysqlContent.PATH_SEPARATOR + command.getTable();
        invokeCommand.setPath(path);
        invokeCommand.setInvokeType(InvokeTypeEnum.MYSQL.getCode());
        return invokeCommand;
    }

    public UserQuery toUserQuery(team.opentech.usher.mysql.pojo.cqe.UserQuery userQuery) {
        UserQuery result = new UserQuery();
        result.copyOf(userQuery);
        result.setUsername(userQuery.getUsername());
        return result;
    }

    public abstract List<UserDTO> toUserDTO(List<CompanyDTO> companyDTOS);

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

    public abstract List<TableDTO> toTableDTO(List<CallNodeDTO> callNodeDTOS);

    public TableDTO toTableDTO(CallNodeDTO callNodeDTOS) {
        TableDTO result = new TableDTO();
        result.setCompanyId(callNodeDTOS.getCompanyId());
        result.setNodeId(callNodeDTOS.getNodeId());
        result.setUrl(callNodeDTOS.getUrl());

        return result;
    }
}
