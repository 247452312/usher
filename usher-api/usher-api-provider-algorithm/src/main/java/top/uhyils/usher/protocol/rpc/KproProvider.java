package top.uhyils.usher.protocol.rpc;


import java.util.Map;
import top.uhyils.usher.pojo.DTO.request.ProjectGenerateRequest;
import top.uhyils.usher.protocol.rpc.base.BaseProvider;

/**
 * 项目生成
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月04日 17时09分
 */
public interface KproProvider extends BaseProvider {

    /**
     * 项目生成接口
     *
     * @param request 项目生成请求
     *
     * @return map<文件名, 内容>
     */
    Map<String, String> projectGenerate(ProjectGenerateRequest request);
}
