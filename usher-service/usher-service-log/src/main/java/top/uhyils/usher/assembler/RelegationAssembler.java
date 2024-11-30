package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.RelegationDO;
import top.uhyils.usher.pojo.DTO.RelegationDTO;
import top.uhyils.usher.pojo.entity.Relegation;

/**
 * 接口降级策略(Relegation)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分23秒
 */
@Mapper(componentModel = "spring")
public abstract class RelegationAssembler extends AbstractAssembler<RelegationDO, Relegation, RelegationDTO> {

}

