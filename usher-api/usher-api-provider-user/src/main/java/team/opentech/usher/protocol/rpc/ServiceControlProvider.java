package team.opentech.usher.protocol.rpc;

import team.opentech.usher.context.UsherContext;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.DTO.request.DelMethodDisableCommand;
import team.opentech.usher.pojo.DTO.request.MethodDisableQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.protocol.rpc.base.BaseProvider;
import java.util.List;

/**
 * 控制接口是否开启和关闭
 * <div>
 *     <h3>
 * redis中的命名规则
 * </h3>
 * <span>
 * 1. hashkey为{@link UsherContext#SERVICE_USEABLE_SWITCH}
 * 2. hash中的key:
 *    2.1 如果是classname 则是接口全名
 *    2.2 如果是methodName 则是{接口全名#方法名称}
 * </span>
 * </div>
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月18日 10时37分
 */
public interface ServiceControlProvider extends BaseProvider {

    /**
     * 获取接口是否允许使用
     *
     * @param request 接口名称
     *
     * @return
     */
    Boolean getMethodDisable(MethodDisableQuery request);

    /**
     * 获取所有接口是否允许使用
     *
     * @return 全部接口
     */
    List<MethodDisableDTO> getAllMethodDisable(DefaultCQE request);


    /**
     * 方法禁用添加修改接口
     *
     * @return 是否成功
     */
    Boolean addOrEditMethodDisable(AddCommand<MethodDisableDTO> request);

    /**
     * 删除对应的禁用接口项
     *
     * @param request 删除请求
     *
     * @return 删除请求
     */
    Boolean delMethodDisable(DelMethodDisableCommand request);
}
