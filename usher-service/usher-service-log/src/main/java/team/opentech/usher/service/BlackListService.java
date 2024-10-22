package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.BlackListDTO;
import team.opentech.usher.pojo.DTO.request.AddBlackIpRequest;
import team.opentech.usher.pojo.DTO.request.GetLogIntervalByIpQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import java.util.List;

/**
 * 黑名单(BlackList)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
public interface BlackListService extends BaseDoService<BlackListDTO> {

    /**
     * 获取ip是否在爬虫黑名单中
     *
     * @param request ip
     *
     * @return
     */
    Boolean getLogIntervalByIp(GetLogIntervalByIpQuery request);

    /**
     * 获取所有的ip黑名单
     *
     * @param request 默认
     *
     * @return 所有的ip黑名单
     */
    List<String> getAllIpBlackList(DefaultCQE request);

    /**
     * 添加黑名单
     *
     * @param request ip
     *
     * @return 是否成功
     *
     * @throws InterruptedException
     */
    Boolean addBlackIp(AddBlackIpRequest request);
}
