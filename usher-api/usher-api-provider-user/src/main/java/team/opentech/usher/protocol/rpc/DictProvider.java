package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.DictDTO;
import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetByCodeQuery;
import team.opentech.usher.pojo.DTO.request.GetByItemArgsQuery;
import team.opentech.usher.pojo.DTO.response.LastPlanDTO;
import team.opentech.usher.pojo.DTO.response.QuickStartDTO;
import team.opentech.usher.pojo.DTO.response.VersionInfoDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.ChangeCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;
import java.util.List;

/**
 * 字典接口API
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时25分
 */
public interface DictProvider extends DTOProvider<DictDTO> {

    /**
     * 新建新的字典项
     *
     * @param request 字典项
     *
     * @return
     */
    Boolean insertItem(AddCommand<DictItemDTO> request);


    /**
     * 获取字典的所有字典项
     *
     * @param request 字典id
     *
     * @return 字典项
     */
    List<DictItemDTO> getItemByDictId(IdQuery request);

    /**
     * 修改字典项
     *
     * @param request 字典项
     *
     * @return 修改是否成功
     */
    Boolean updateItem(ChangeCommand<DictItemDTO> request);

    /**
     * 删除字典项
     *
     * @param request 字典项id
     *
     * @return 是否删除成功
     */
    Boolean deleteItem(IdCommand request);


    /**
     * 清理某一个字典 即 删除所有字典项
     *
     * @param request 字典id
     *
     * @return 是否成功
     */
    Boolean cleanDictItem(IdCommand request);


    /**
     * 获取字典项
     *
     * @param request 字典项id
     *
     * @return 字典项
     */
    DictItemDTO getItemById(IdQuery request);

    /**
     * 根据某几列获取item数据
     *
     * @param request 根据列名获取信息
     *
     * @return 分页数据(也可以设置不分页)
     */
    Page<DictItemDTO> getByItemArgs(GetByItemArgsQuery request);


    /**
     * 获取版本信息response
     *
     * @param request 默认请求
     *
     * @return 版本信息
     */
    VersionInfoDTO getVersionInfoResponse(DefaultCQE request);


    /**
     * 获取下一步计划
     *
     * @param request 默认
     *
     * @return 下一步计划
     */
    LastPlanDTO getLastPlanResponse(DefaultCQE request);


    /**
     * 获取全部的按钮菜单
     *
     * @param request 默认请求
     *
     * @return 图标class
     */
    List<String> getAllMenuIcon(DefaultCQE request);


    /**
     * 获取code对应的字典对应的所有项
     *
     * @param request 字典code
     *
     * @return code对应的字典对应的所有项
     */
    List<DictItemDTO> getByCode(GetByCodeQuery request);


    /**
     * 获取开始界面快捷入口信息
     *
     * @param request 默认请求
     *
     * @return 开始界面快捷入口信息
     */
    QuickStartDTO getQuickStartResponse(DefaultCQE request);
}
