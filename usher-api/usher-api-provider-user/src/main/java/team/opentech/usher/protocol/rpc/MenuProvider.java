package team.opentech.usher.protocol.rpc;

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
import team.opentech.usher.protocol.rpc.base.DTOProvider;
import java.util.List;

/**
 * 菜单接口API
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月28日 12时41分
 */
public interface MenuProvider extends DTOProvider<MenuDTO> {

    /**
     * 获取主页的菜单
     *
     * @param request 请求
     *
     * @return 主页菜单 包括主页信息 logo信息 菜单信息
     */
    IndexMenuTreeDTO getIndexMenu(DefaultCQE request);


    /**
     * 将许多权限集添加到一个菜单
     *
     * @param request 将许多权限集添加到一个菜单的请求
     *
     * @return 是否成功
     */
    Boolean putDeptsToMenu(PutDeptsToMenuCommand request);

    /**
     * 获取菜单tree,并将格式转为前台的格式(menu.html用)
     *
     * @param request 请求
     *
     * @return 格式处理好菜单
     */
    MenuHtmlTreeDTO getMenuTree(GetByIFrameAndDeptsQuery request);


    /**
     * 1.删除对应id节点以及所有子节点
     * 2.删除权限集表与菜单连接表中的对应关系
     *
     * @param req 要删除的节点id
     *
     * @return 是否删除成功
     */
    Boolean removeMenu(IdCommand req);

    /**
     * 根据菜单id获取属于这个菜单的权限集以及全部权限集
     *
     * @param req 包含菜单id的请求
     *
     * @return 权限集们
     */
    List<GetDeptsByMenuIdDTO> getDeptsByMenuId(IdQuery req);

    /**
     * 获取所有叶子菜单(包含羁绊标记)
     *
     * @param request 权限集id
     *
     * @return 所有叶子菜单(包含羁绊标记)
     */
    List<GetAllMenuWithHaveMarkDTO> getAllMenuWithHaveMark(IdQuery request);

}
