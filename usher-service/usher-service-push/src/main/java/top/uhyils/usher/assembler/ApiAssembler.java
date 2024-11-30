package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.ApiDO;
import top.uhyils.usher.pojo.DTO.ApiDTO;
import top.uhyils.usher.pojo.entity.Api;

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
