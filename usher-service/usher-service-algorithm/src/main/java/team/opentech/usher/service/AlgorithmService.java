package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.AlgorithmDTO;
import team.opentech.usher.pojo.DTO.request.CellAlgorithmRequest;
import team.opentech.usher.pojo.DTO.response.CellAlgorithmResponse;

/**
 * 算法表(Algorithm)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分08秒
 */
public interface AlgorithmService extends BaseDoService<AlgorithmDTO> {

    /**
     * 调用算法
     *
     * @param request
     *
     * @return
     */
    CellAlgorithmResponse cellAlgorithm(CellAlgorithmRequest request);


}
