package top.uhyils.usher.service;


import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.event.CleanSubSpaceEvent;

/**
 * 子空间(AiSubspace)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSubspaceService extends BaseDoService<AiSubspaceDTO> {

    /**
     * 清空某一个独立空间的所有子空间
     *
     * @param event
     */
    void cleanSubSpaceEvent(CleanSubSpaceEvent event);
}
