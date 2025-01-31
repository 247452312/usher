package team.opentech.usher.repository;

import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.entity.MethodDisable;
import team.opentech.usher.repository.base.BaseRepository;
import java.util.List;

/**
 * 数据字典(Dict)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分37秒
 */
public interface ServiceControlRepository extends BaseRepository {

    /**
     * 检查方法是否允许
     *
     * @param fullMethodName
     *
     * @return
     */
    Boolean checkMethodDisable(String fullMethodName);

    /**
     * 检查接口是否允许
     *
     * @param className
     * @param methodType
     *
     * @return
     */
    Boolean checkClassDisable(String className, ReadWriteTypeEnum methodType);

    /**
     * 获取所有已经配置的接口禁用
     *
     * @return
     */
    List<MethodDisableDTO> findAll();

    /**
     * 保存methodDisable
     *
     * @param methodDisable
     */
    void saveMethodDisable(MethodDisable methodDisable);

    /**
     * 删除方法禁用
     *
     * @param methodDisable
     */
    void delMethodDisable(MethodDisable methodDisable);
}
