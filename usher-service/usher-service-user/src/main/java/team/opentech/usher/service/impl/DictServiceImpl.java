package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.DictAssembler;
import team.opentech.usher.assembler.DictItemAssembler;
import team.opentech.usher.assembler.MenuAssembler;
import team.opentech.usher.enums.CacheTypeEnum;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DO.DictDO;
import team.opentech.usher.pojo.DTO.DictDTO;
import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.response.LastPlanDTO;
import team.opentech.usher.pojo.DTO.response.QuickStartDTO;
import team.opentech.usher.pojo.DTO.response.VersionInfoDTO;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.Dict;
import team.opentech.usher.pojo.entity.DictItem;
import team.opentech.usher.pojo.entity.Menu;
import team.opentech.usher.pojo.entity.type.Code;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DictItemRepository;
import team.opentech.usher.repository.DictRepository;
import team.opentech.usher.repository.MenuRepository;
import team.opentech.usher.service.DictService;
import team.opentech.usher.util.LogUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 数据字典(Dict)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分38秒
 */
@Service
@ReadWriteMark(tables = {"sys_dict"}, cacheType = CacheTypeEnum.ALL_TYPE)
public class DictServiceImpl extends AbstractDoService<DictDO, Dict, DictDTO, DictRepository, DictAssembler> implements DictService {

    /**
     * 首页版本信息展示 字典code
     */
    private static final String VERSION_CODE = "myVersionCode";

    /**
     * 首页下一步计划展示 字典code
     */
    private static final String LAST_PLAN_CODE = "lastPlan";

    /**
     * 图标的icon-class
     */
    private static final String MENU_ICON_CLASS_CODE = "icon-class";

    /**
     * 首页下一步计划展示 字典code
     */
    private static final String QUICK_START_CODE = "quickStart";

    @Resource
    private DictItemRepository dictItemRepository;

    @Resource
    private DictItemAssembler dictItemAssembler;

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private MenuAssembler menuAssembler;

    public DictServiceImpl(DictAssembler assembler, DictRepository repository) {
        super(assembler, repository);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dict_item"})
    public Boolean insertItem(DictItemDTO dto) {
        DictItem dictItem = dictItemAssembler.toEntity(dto);
        dictItemRepository.save(dictItem);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public List<DictItemDTO> getItemByDictId(Identifier dictId) {
        Dict dict = new Dict(dictId.getId());
        dict.fillItem(dictItemRepository);
        return dict.toItem().stream().map(dictItemAssembler::toDTO).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dict_item"})
    public Boolean updateItem(DictItemDTO dto, List<Arg> args) {
        DictItem dict = dictItemAssembler.toEntity(dto);
        dictItemRepository.change(dict, args);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dict_item"})
    public Boolean deleteItem(Identifier dictItemId) {
        DictItem dictItem = new DictItem(dictItemId.getId());
        dictItem.completion(dictItemRepository);
        dictItemRepository.remove(Collections.singletonList(dictItem));
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dict_item"})
    public Boolean cleanDictItem(Identifier dictId) {
        Dict dict = new Dict(dictId.getId());
        dict.fillItem(dictItemRepository);
        dict.cleanItem(dictItemRepository);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public DictItemDTO getItemById(Identifier dictItemId) {
        DictItem dictItem = new DictItem(dictItemId.getId());
        dictItem.completion(dictItemRepository);
        return dictItemAssembler.toDTO(dictItem);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public Page<DictItemDTO> getByItemArgs(Identifier dictId, List<Arg> args, Order order, Limit limit) {
        Page<DictItem> dictItemPage = dictItemRepository.find(dictId, args, order, limit);
        return dictItemAssembler.toDTO(dictItemPage);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public VersionInfoDTO getVersionInfoResponse() {
        Dict dictItemCode = new Dict(VERSION_CODE);
        List<DictItem> item = dictItemCode.findItemByCode(dictItemRepository);
        List<DictItemDTO> items = item.stream().map(t -> dictItemAssembler.toDTO(t)).collect(Collectors.toList());
        return VersionInfoDTO.build(items);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public LastPlanDTO getLastPlanResponse() {
        Dict dictItemCode = new Dict(LAST_PLAN_CODE);
        List<DictItem> item = dictItemCode.findItemByCode(dictItemRepository);
        List<DictItemDTO> items = item.stream().map(t -> dictItemAssembler.toDTO(t)).collect(Collectors.toList());
        return LastPlanDTO.build(items);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"})
    public List<String> getAllMenuIcon() {
        Dict dictItemCode = new Dict(MENU_ICON_CLASS_CODE);
        List<DictItem> item = dictItemCode.findItemByCode(dictItemRepository);
        List<DictItemDTO> items = item.stream().map(t -> dictItemAssembler.toDTO(t)).collect(Collectors.toList());
        return items.stream().map(DictItemDTO::getValue).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dict_item"}, cacheType = CacheTypeEnum.ALL_TYPE)
    public List<DictItemDTO> getByCode(Code code) {
        Dict dictItemCode = new Dict(code.getCode());
        List<DictItem> item = dictItemCode.findItemByCode(dictItemRepository);
        return item.stream().map(t -> dictItemAssembler.toDTO(t)).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dict", "sys_dict_item"})
    public QuickStartDTO getQuickStartResponse() {
        Dict dictItemCode = new Dict(QUICK_START_CODE);
        List<DictItem> item = dictItemCode.findItemByCode(dictItemRepository);
        List<DictItemDTO> items = item.stream().map(t -> dictItemAssembler.toDTO(t)).collect(Collectors.toList());
        List<MenuDTO> result = items.stream().map(t -> new Menu(Long.valueOf(t.getValue()))).map(menuRepository::find).map(t -> menuAssembler.toDTO(t)).collect(Collectors.toList());
        result.stream().filter(t -> !t.getType()).map(MenuDTO::getName).forEach(t -> LogUtil.error("服务字典中快捷入口(" + t + ") 不是叶子结点"));
        return QuickStartDTO.build(result);
    }
}
