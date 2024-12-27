package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.AlgorithmDO;
import top.uhyils.usher.pojo.DTO.AlgorithmDTO;
import top.uhyils.usher.pojo.entity.Algorithm;

/**
 * 算法表(Algorithm)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分07秒
 */
@Mapper(componentModel = "spring")
public abstract class AlgorithmAssembler extends AbstractAssembler<AlgorithmDO, Algorithm, AlgorithmDTO> {

}
