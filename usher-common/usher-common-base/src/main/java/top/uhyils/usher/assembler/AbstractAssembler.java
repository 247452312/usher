package top.uhyils.usher.assembler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.ap.spi.util.IntrospectorUtils;
import top.uhyils.usher.enums.Symbol;
import top.uhyils.usher.pojo.DO.base.BaseDO;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.util.LogUtil;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月26日 08时51分
 */
public abstract class AbstractAssembler<DO extends BaseDO, ENTITY extends AbstractDoEntity<DO>, DTO extends IdDTO> implements BaseEntityAssembler<DO, ENTITY, DTO> {

    /**
     * get方法开头
     */
    public static final String GET_METHOD_HEAD = "get";

    @Override
    public List<Arg> toArgs(DTO dto) {
        List<Arg> result = new ArrayList<>();
        Class<? extends IdDTO> dtoClass = dto.getClass();
        /*DTO中所有属性获取都是public的get方法*/
        Method[] methods = dtoClass.getMethods();
        try {
            for (Method method : methods) {
                if (!method.getName().startsWith(GET_METHOD_HEAD)) {
                    continue;
                }
                // 真实值
                Object invoke = method.invoke(dto);
                if (invoke != null) {
                    String substring = method.getName().substring(GET_METHOD_HEAD.length());
                    String fieldName = IntrospectorUtils.decapitalize(substring);
                    result.add(Arg.as(fieldName, Symbol.EQ, invoke));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            LogUtil.error(e);
            return Collections.emptyList();
        }
        return result;
    }

    @Override
    public DTO toDTO(ENTITY entity) {
        return toDTO(entity.toDataAndValidate());
    }

    @Override
    public Page<DTO> pageToDTO(Page<ENTITY> page) {
        Page<DTO> result = new Page<>();
        List<ENTITY> list = page.getList();
        List<DTO> dtos = list.stream().map(t -> toDTO(t)).collect(Collectors.toList());
        result.setList(dtos);
        result.setSize(page.getSize());
        result.setPageNum(page.getPageNum());
        result.setCount(page.getCount());
        result.setTotalPage(page.getTotalPage());
        return result;
    }
}
