package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ContentDTO;
import team.opentech.usher.protocol.rpc.ContentProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 公共参数(Content)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分19秒
 */
@RpcService
public class ContentProviderImpl extends BaseDefaultProvider<ContentDTO> implements ContentProvider {


    @Autowired
    private ContentService service;


    @Override
    protected BaseDoService<ContentDTO> getService() {
        return service;
    }

}

