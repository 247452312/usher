package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.TraceDetailStatisticsView;
import top.uhyils.usher.pojo.DO.TraceInfoDO;
import top.uhyils.usher.pojo.DTO.TraceDetailStatisticsDTO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.entity.TraceInfo;

/**
 * (TraceInfo)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Mapper(componentModel = "spring")
public abstract class TraceInfoAssembler extends AbstractAssembler<TraceInfoDO, TraceInfo, TraceInfoDTO> {

    /**
     * 视图模型转DTO
     *
     * @param view
     *
     * @return
     */
    public abstract TraceDetailStatisticsDTO viewToDTO(TraceDetailStatisticsView view);


    public abstract Page<TraceDetailStatisticsDTO> pageViewToDTO(Page<TraceDetailStatisticsView> result);
}

