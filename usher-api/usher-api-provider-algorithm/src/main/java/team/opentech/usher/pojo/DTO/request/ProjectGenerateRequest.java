package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.DefaultCQE;
import java.util.List;

/**
 * 项目生成请求 其中{@link ProjectGenerateRequest#list} 详细介绍见{@link DbInformationDTO}
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月27日 07时04分
 */
public class ProjectGenerateRequest extends DefaultCQE {

    /**
     * 多个数据库信息
     */
    private List<DbInformationDTO> list;


    public List<DbInformationDTO> getList() {
        return list;
    }

    public void setList(List<DbInformationDTO> list) {
        this.list = list;
    }
}
