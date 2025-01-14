package top.uhyils.usher.assembler;

import java.util.List;
import org.mapstruct.Mapper;
import top.uhyils.usher.mysql.pojo.DTO.CompanyInfo;
import top.uhyils.usher.mysql.pojo.cqe.TableQuery;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.pojo.cqe.UserQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 18时04分
 */

@Mapper(componentModel = "spring")
public abstract class GatewayAssembler implements BaseAssembler {


    public abstract UserQuery toUserQuery(top.uhyils.usher.mysql.pojo.cqe.UserQuery userQuery);

    public abstract List<CompanyInfo> toCompanyInfo(List<CompanyDTO> companyDTOS);

    public UserDTO toUserDTO(String ip, CompanyDTO companyDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(companyDTO.getPersonName());
        userDTO.setUsername(companyDTO.getName());
        userDTO.setPhone(companyDTO.getPersonPhone());
        userDTO.setStatus(1);
        userDTO.setIp(ip);
        userDTO.setId(companyDTO.getId());
        return userDTO;
    }

    public CallNodeQuery toCallNode(TableQuery tableQuery) {
        CallNodeQuery invokeCommand = new CallNodeQuery();
        invokeCommand.copyOf(tableQuery);
        return invokeCommand;
    }

}
