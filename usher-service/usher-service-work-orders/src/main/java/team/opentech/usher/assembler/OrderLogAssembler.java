package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OrderLogDO;
import team.opentech.usher.pojo.DTO.OrderLogDTO;
import team.opentech.usher.pojo.entity.OrderLog;
import org.mapstruct.Mapper;

/**
 * 日志表(OrderLog)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分17秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderLogAssembler extends AbstractAssembler<OrderLogDO, OrderLog, OrderLogDTO> {

}
