package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.DeviceManageFacade;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 16时49分
 */
@Facade
public class DeviceManageFacadeImpl implements DeviceManageFacade {


    @Override
    public Object executeInstruction(String deviceInstructionNo, String context, String uniqueMark) {
        return Boolean.TRUE;
    }
}
