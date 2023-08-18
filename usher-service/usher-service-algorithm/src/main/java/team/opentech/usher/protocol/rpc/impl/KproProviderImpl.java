package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.request.DbInformationDTO;
import team.opentech.usher.pojo.DTO.request.ProjectGenerateRequest;
import team.opentech.usher.protocol.rpc.KproProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.KproService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 21时06分
 */
@RpcService
public class KproProviderImpl implements KproProvider {

    @Autowired
    private KproService service;

    @Override
    public Map<String, String> projectGenerate(ProjectGenerateRequest request) {
        List<DbInformationDTO> list = request.getList();
        Map<String, String> result = service.projectGenerate(list);
        return result;
    }
}
