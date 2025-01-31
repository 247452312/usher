package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.NodeDO;
import team.opentech.usher.pojo.DTO.NodeDTO;
import team.opentech.usher.pojo.entity.Node;

/**
 * 转换节点表(Node)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class NodeAssembler extends AbstractAssembler<NodeDO, Node, NodeDTO> {

}
