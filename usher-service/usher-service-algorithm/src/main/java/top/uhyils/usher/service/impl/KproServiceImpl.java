package top.uhyils.usher.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import top.uhyils.usher.pojo.DTO.request.DbInformationDTO;
import top.uhyils.usher.pojo.entity.DbInformation;
import top.uhyils.usher.service.KproService;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 21时23分
 */
@Service
public class KproServiceImpl implements KproService {

    @Override
    public Map<String, String> projectGenerate(List<DbInformationDTO> list) {
        List<DbInformation> informs = list.stream().map(DbInformation::new).collect(Collectors.toList());
        Map<String, String> resultList = new HashMap<>();
        for (DbInformation inform : informs) {
            Map<String, String> result = inform.result();
            resultList.putAll(result);
        }
        return resultList;
    }
}
