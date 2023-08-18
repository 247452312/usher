package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.ApiSubscribeDO;
import team.opentech.usher.pojo.DTO.ApiSubscribeDTO;
import team.opentech.usher.pojo.DTO.request.SubscribeRequest;
import team.opentech.usher.pojo.entity.ApiSubscribe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * api订阅表(ApiSubscribe)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分53秒
 */
@Mapper(componentModel = "spring")
public abstract class ApiSubscribeAssembler extends AbstractAssembler<ApiSubscribeDO, ApiSubscribe, ApiSubscribeDTO> {

    @Mapping(source = "sendType", target = "type")
    public abstract ApiSubscribeDTO toDTO(SubscribeRequest request);
}
