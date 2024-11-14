package team.opentech.usher.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiSpaceAssembler;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.cqe.AddUserToSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import team.opentech.usher.pojo.cqe.SpaceCreateCommand;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.event.CleanSpaceUserEvent;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.repository.AiSpaceUserLinkRepository;
import team.opentech.usher.service.AiSpaceService;
import team.opentech.usher.util.Asserts;

/**
 * 独立空间(AiSpace)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_space"})
public class AiSpaceServiceImpl extends AbstractDoService<AiSpaceDO, AiSpace, AiSpaceDTO, AiSpaceRepository, AiSpaceAssembler> implements AiSpaceService {

    @Resource
    private AiSpaceUserLinkRepository userLinkRepository;

    public AiSpaceServiceImpl(AiSpaceAssembler assembler, AiSpaceRepository repository) {
        super(assembler, repository);
    }

    @Override
    @Transactional
    public Boolean create(SpaceCreateCommand command) {
        // 1.创建一个独立空间
        AiSpace space = assem.toEntity(command);
        space.saveSelf(rep);
        // 2.创建人设置为默认管理员
        space.addUser(userLinkRepository, command.getUser(), Boolean.TRUE);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addUserToSpace(AddUserToSpaceCommand command) {
        AiSpace aiSpace = rep.find(Identifier.build(command.getSpaceId()));
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        aiSpace.addUser(userLinkRepository, command.getUserId(), command.getAdmin());
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeUserFromSpace(RemoveUserFromSpaceCommand command) {
        AiSpace aiSpace = rep.find(Identifier.build(command.getSpaceId()));
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        return aiSpace.removeUser(userLinkRepository, command.getUserId());
    }

    @Override
    public Boolean removeSpace(RemoveSpaceCommand command) {
        AiSpace aiSpace = rep.find(Identifier.build(command.getSpaceId()));
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        aiSpace.removeSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public void cleanSpaceUserEvent(CleanSpaceUserEvent event) {
        userLinkRepository.removeBySpaceId(event.getSpaceId());
    }
}
