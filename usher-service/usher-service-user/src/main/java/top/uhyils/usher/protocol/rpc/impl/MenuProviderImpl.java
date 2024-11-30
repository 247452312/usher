package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.MenuDTO;
import top.uhyils.usher.pojo.DTO.request.GetByIFrameAndDeptsQuery;
import top.uhyils.usher.pojo.DTO.request.PutDeptsToMenuCommand;
import top.uhyils.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import top.uhyils.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import top.uhyils.usher.pojo.DTO.response.IndexMenuTreeDTO;
import top.uhyils.usher.pojo.DTO.response.MenuHtmlTreeDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.entity.type.Iframe;
import top.uhyils.usher.protocol.rpc.MenuProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.MenuService;

/**
 * 菜单接口实现类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月28日 12时48分
 */
@RpcService
public class MenuProviderImpl extends BaseDefaultProvider<MenuDTO> implements MenuProvider {

    @Autowired
    private MenuService service;

    @Override
    public IndexMenuTreeDTO getIndexMenu(DefaultCQE request) {
        return service.getIndexMenu();

    }

    @Override
    public Boolean putDeptsToMenu(PutDeptsToMenuCommand request) {
        return service.putDeptsToMenu(request.getMenuId(), request.getDeptIds());
    }

    @Override
    public MenuHtmlTreeDTO getMenuTree(GetByIFrameAndDeptsQuery request) {
        Iframe iframe = new Iframe(request.getiFrame());
        return service.getMenuTree(iframe);

    }

    @Override
    public Boolean removeMenu(IdCommand request) {
        return service.removeMenu(request.getId());

    }

    @Override
    public List<GetDeptsByMenuIdDTO> getDeptsByMenuId(IdQuery request) {
        return service.getDeptsByMenuId(request.getId());

    }

    @Override
    public List<GetAllMenuWithHaveMarkDTO> getAllMenuWithHaveMark(IdQuery request) {
        return service.getAllMenuWithHaveMark(request.getId());

    }

    @Override
    protected BaseDoService<MenuDTO> getService() {
        return service;
    }
}
