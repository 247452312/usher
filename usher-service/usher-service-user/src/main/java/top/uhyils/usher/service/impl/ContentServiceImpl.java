package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ContentAssembler;
import top.uhyils.usher.pojo.DO.ContentDO;
import top.uhyils.usher.pojo.DTO.ContentDTO;
import top.uhyils.usher.pojo.entity.Content;
import top.uhyils.usher.repository.ContentRepository;
import top.uhyils.usher.service.ContentService;

/**
 * 公共参数(Content)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分19秒
 */
@Service
@ReadWriteMark(tables = {"sys_content"})
public class ContentServiceImpl extends AbstractDoService<ContentDO, Content, ContentDTO, ContentRepository, ContentAssembler> implements ContentService {

    public ContentServiceImpl(ContentAssembler assembler, ContentRepository repository) {
        super(assembler, repository);
    }


}
