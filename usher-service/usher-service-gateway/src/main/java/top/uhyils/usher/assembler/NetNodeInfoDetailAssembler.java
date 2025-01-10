package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Mapper(componentModel = "spring")
public abstract class NetNodeInfoDetailAssembler extends AbstractAssembler<NetNodeInfoDetailDO, NetNodeInfoDetail, NetNodeInfoDetailDTO> {

}
