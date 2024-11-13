package team.opentech.usher.pojo.DTO;

import team.opentech.usher.pojo.DTO.base.IdDTO;

/**
 * 独立空间表(AiSpace)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpaceDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 独立空间名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
