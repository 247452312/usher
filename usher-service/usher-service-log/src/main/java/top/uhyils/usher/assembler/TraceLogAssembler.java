package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.TraceLogDO;
import top.uhyils.usher.pojo.DTO.TraceLogDTO;
import top.uhyils.usher.pojo.entity.TraceLog;

/**
 * (TraceLog)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Mapper(componentModel = "spring")
public abstract class TraceLogAssembler extends AbstractAssembler<TraceLogDO, TraceLog, TraceLogDTO> {

}

