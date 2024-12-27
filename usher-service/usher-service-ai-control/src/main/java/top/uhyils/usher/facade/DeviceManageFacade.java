package top.uhyils.usher.facade;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 16时49分
 */
public interface DeviceManageFacade extends BaseFacade {


    /**
     * 执行
     */
    Object executeInstruction(String deviceInstructionNo, String context, String uniqueMark);
}
