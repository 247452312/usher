package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.ApiDO;
import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.entity.Api;
import org.mapstruct.Mapper;

/**
 * api表(Api)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分45秒
 */
@Mapper(componentModel = "spring")
public abstract class ApiAssembler extends AbstractAssembler<ApiDO, Api, ApiDTO> {

}
