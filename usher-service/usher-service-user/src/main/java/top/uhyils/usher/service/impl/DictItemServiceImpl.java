package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.assembler.DictItemAssembler;
import top.uhyils.usher.pojo.DO.DictItemDO;
import top.uhyils.usher.pojo.DTO.DictItemDTO;
import top.uhyils.usher.pojo.entity.DictItem;
import top.uhyils.usher.repository.DictItemRepository;
import top.uhyils.usher.service.DictItemService;

/**
 * 数据字典子项(DictItem)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分43秒
 */
@Service
public class DictItemServiceImpl extends AbstractDoService<DictItemDO, DictItem, DictItemDTO, DictItemRepository, DictItemAssembler> implements DictItemService {

    public DictItemServiceImpl(DictItemAssembler assembler, DictItemRepository repository) {
        super(assembler, repository);
    }


}
