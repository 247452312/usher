package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.DictItemDO;
import top.uhyils.usher.pojo.DTO.DictItemDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.entity.DictItem;

/**
 * 数据字典子项(DictItem)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分40秒
 */
@Mapper(componentModel = "spring")
public abstract class DictItemAssembler extends AbstractAssembler<DictItemDO, DictItem, DictItemDTO> {

    public abstract Page<DictItemDTO> toDTO(Page<DictItem> page);
}

