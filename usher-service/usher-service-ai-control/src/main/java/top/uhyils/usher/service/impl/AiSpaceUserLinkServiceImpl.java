package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.AiSpaceUserLinkAssembler;
import top.uhyils.usher.pojo.DO.AiSpaceUserLinkDO;
import top.uhyils.usher.pojo.DTO.AiSpaceUserLinkDTO;
import top.uhyils.usher.pojo.entity.AiSpaceUserLink;
import top.uhyils.usher.repository.AiSpaceUserLinkRepository;
import top.uhyils.usher.service.AiSpaceUserLinkService;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_space_user_link"})
public class AiSpaceUserLinkServiceImpl extends AbstractDoService<AiSpaceUserLinkDO, AiSpaceUserLink, AiSpaceUserLinkDTO, AiSpaceUserLinkRepository, AiSpaceUserLinkAssembler> implements AiSpaceUserLinkService {

    public AiSpaceUserLinkServiceImpl(AiSpaceUserLinkAssembler assembler, AiSpaceUserLinkRepository repository) {
        super(assembler, repository);
    }


}
