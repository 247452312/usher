package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.request.DbInformationDTO;
import top.uhyils.usher.pojo.DTO.request.ProjectGenerateRequest;
import top.uhyils.usher.protocol.rpc.KproProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.KproService;


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
