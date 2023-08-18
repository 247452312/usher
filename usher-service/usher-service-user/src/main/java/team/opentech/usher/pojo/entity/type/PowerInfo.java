package team.opentech.usher.pojo.entity.type;

import team.opentech.usher.assembler.PowerAssembler;
import team.opentech.usher.pojo.DO.PowerDO;
import team.opentech.usher.pojo.entity.Power;
import team.opentech.usher.repository.PowerRepository;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月28日 13时04分
 */
public class PowerInfo implements BaseType {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    public PowerInfo(String interfaceName, String methodName) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Power toEntity(PowerAssembler assem) {
        PowerDO powerDO = new PowerDO();
        powerDO.setInterfaceName(interfaceName);
        powerDO.setMethodName(methodName);
        return assem.toEntity(powerDO);
    }

    public Boolean exist(PowerRepository rep) {
        return rep.exist(this);
    }
}
