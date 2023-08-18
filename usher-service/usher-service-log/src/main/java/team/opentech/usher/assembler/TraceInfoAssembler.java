package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.TraceDetailStatisticsView;
import team.opentech.usher.pojo.DO.TraceInfoDO;
import team.opentech.usher.pojo.DTO.TraceDetailStatisticsDTO;
import team.opentech.usher.pojo.DTO.TraceInfoDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.entity.TraceInfo;
import org.mapstruct.Mapper;

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

