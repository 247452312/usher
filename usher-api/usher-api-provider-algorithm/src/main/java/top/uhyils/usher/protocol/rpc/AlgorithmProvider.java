package top.uhyils.usher.protocol.rpc;


import top.uhyils.usher.pojo.DTO.AlgorithmDTO;
import top.uhyils.usher.pojo.DTO.request.CellAlgorithmRequest;
import top.uhyils.usher.pojo.DTO.response.CellAlgorithmResponse;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

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
