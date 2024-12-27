package top.uhyils.usher.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.facade.DictFacade;
import top.uhyils.usher.facade.JvmFacade;
import top.uhyils.usher.pojo.DTO.response.AlgorithmStatisticsResponse;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.DTO.response.LastPlanDTO;
import top.uhyils.usher.pojo.DTO.response.QuickStartDTO;
import top.uhyils.usher.pojo.DTO.response.VersionInfoDTO;
import top.uhyils.usher.pojo.DTO.response.welcome.WelcomeResponse;
import top.uhyils.usher.service.DistributeService;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月10日 08时25分
 */
@Service
public class DistributeServiceImpl implements DistributeService {


    @Autowired
    private JvmFacade jvmFacade;

    @Autowired
    private DictFacade dictFacade;

    @Override
    public WelcomeResponse getWelcomeData() {
        WelcomeResponse welcomeResponse = new WelcomeResponse();
        /* 统计信息 */
        JvmDataStatisticsDTO jvmDataStatisticsResponse = jvmFacade.jvmStatisticDate();
        welcomeResponse.setJvmDataStatisticsResponse(jvmDataStatisticsResponse);

        /*快捷启动*/
        QuickStartDTO quickStartResponse = dictFacade.quickStartInfo();
        welcomeResponse.setQuickStartResponse(quickStartResponse);

        /*jvm图表信息*/
        JvmInfoLogDTO jvmInfoLogResponse = jvmFacade.jvmInfoLog();
        welcomeResponse.setJvmInfoLogResponse(jvmInfoLogResponse);

        /*算法信息 还没有*/
        welcomeResponse.setAlgorithmStatisticsResponse(AlgorithmStatisticsResponse.build(new ArrayList<>()));

        /*版本信息*/
        VersionInfoDTO versionInfoResponse = dictFacade.versionInfo();
        welcomeResponse.setVersionInfoResponse(versionInfoResponse);

        /* 下一步计划 */
        LastPlanDTO lastPlanResponse = dictFacade.lastPlan();
        welcomeResponse.setLastPlanResponse(lastPlanResponse);
        return welcomeResponse;
    }
}
