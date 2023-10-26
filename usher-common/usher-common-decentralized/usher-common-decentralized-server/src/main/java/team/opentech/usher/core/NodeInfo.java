package team.opentech.usher.core;

import team.opentech.usher.enums.RoleEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月26日 08时52分
 */
public class NodeInfo {

    /**
     * 当前用户角色
     */
    private RoleEnum role;

    public static NodeInfo getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {

        private static final NodeInfo INSTANCE = new NodeInfo();
    }
}
