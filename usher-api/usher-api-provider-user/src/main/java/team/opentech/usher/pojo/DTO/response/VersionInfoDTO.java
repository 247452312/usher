package team.opentech.usher.pojo.DTO.response;

import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.base.BaseDTO;
import java.util.List;

/**
 * 版本信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月21日 07时28分
 */
public class VersionInfoDTO implements BaseDTO {

    /**
     * 版本信息-> 数据字典中
     */
    List<DictItemDTO> infos;

    public static VersionInfoDTO build(List<DictItemDTO> infos) {
        VersionInfoDTO versionInfoResponse = new VersionInfoDTO();
        versionInfoResponse.setInfos(infos);
        return versionInfoResponse;

    }

    public List<DictItemDTO> getInfos() {
        return infos;
    }

    public void setInfos(List<DictItemDTO> infos) {
        this.infos = infos;
    }
}
