package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ContentAssembler;
import team.opentech.usher.pojo.DO.ContentDO;
import team.opentech.usher.pojo.DTO.ContentDTO;
import team.opentech.usher.pojo.entity.Content;
import team.opentech.usher.repository.ContentRepository;
import team.opentech.usher.service.ContentService;
import org.springframework.stereotype.Service;

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
