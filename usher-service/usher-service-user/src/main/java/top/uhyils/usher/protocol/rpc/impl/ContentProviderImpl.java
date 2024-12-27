package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ContentDTO;
import top.uhyils.usher.protocol.rpc.ContentProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ContentService;

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

