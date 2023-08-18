package team.opentech.usher.protocol.rpc;


import team.opentech.usher.pojo.DTO.AlgorithmDTO;
import team.opentech.usher.pojo.DTO.request.CellAlgorithmRequest;
import team.opentech.usher.pojo.DTO.response.CellAlgorithmResponse;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * 算法接口
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月04日 17时09分
 */
public interface AlgorithmProvider extends DTOProvider<AlgorithmDTO> {

    /**
     * 调用算法
     *
     * @param request
     *
     * @return
     */
    CellAlgorithmResponse cellAlgorithm(CellAlgorithmRequest request);

}
