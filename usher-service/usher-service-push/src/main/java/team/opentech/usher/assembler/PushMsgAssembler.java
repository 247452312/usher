package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.PushMsgDO;
import team.opentech.usher.pojo.DTO.PushMsgDTO;
import team.opentech.usher.pojo.entity.PushMsg;
import org.mapstruct.Mapper;

/**
 * 推送日志表(PushMsg)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分03秒
 */
@Mapper(componentModel = "spring")
public abstract class PushMsgAssembler extends AbstractAssembler<PushMsgDO, PushMsg, PushMsgDTO> {

}
