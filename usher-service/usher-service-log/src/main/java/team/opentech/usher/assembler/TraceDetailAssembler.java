package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.TraceDetailDO;
import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.entity.TraceDetail;
import org.mapstruct.Mapper;

/**
 * (TraceDetail)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
@Mapper(componentModel = "spring")
public abstract class TraceDetailAssembler extends AbstractAssembler<TraceDetailDO, TraceDetail, TraceDetailDTO> {

}

