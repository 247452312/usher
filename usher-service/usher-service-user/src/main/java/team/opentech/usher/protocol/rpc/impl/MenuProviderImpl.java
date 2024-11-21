package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.DTO.request.GetByIFrameAndDeptsQuery;
import team.opentech.usher.pojo.DTO.request.PutDeptsToMenuCommand;
import team.opentech.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import team.opentech.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import team.opentech.usher.pojo.DTO.response.IndexMenuTreeDTO;
import team.opentech.usher.pojo.DTO.response.MenuHtmlTreeDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.entity.type.Iframe;
import team.opentech.usher.protocol.rpc.MenuProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.MenuService;

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
