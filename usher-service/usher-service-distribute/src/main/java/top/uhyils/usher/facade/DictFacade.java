package top.uhyils.usher.facade;

import top.uhyils.usher.pojo.DTO.response.LastPlanDTO;
import top.uhyils.usher.pojo.DTO.response.QuickStartDTO;
import top.uhyils.usher.pojo.DTO.response.VersionInfoDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月10日 08时29分
 */
public interface DictFacade extends BaseFacade {

    /**
     * 快速开始
     *
     * @return
     */
    QuickStartDTO quickStartInfo();

    /**
     * 版本信息
     *
     * @return
     */
    VersionInfoDTO versionInfo();


    /**
     * 下一步计划
     *
     * @return
     */
    LastPlanDTO lastPlan();
}
