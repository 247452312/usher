package team.opentech.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiSpaceAssembler;
import team.opentech.usher.assembler.AiSubspaceAssembler;
import team.opentech.usher.facade.UserFacade;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.AddUserToSpaceCommand;
import team.opentech.usher.pojo.cqe.ChangeSubSpaceCommand;
import team.opentech.usher.pojo.cqe.CreateSubSpaceCommand;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.FindSubSpaceBySpaceIdQuery;
import team.opentech.usher.pojo.cqe.RemoveSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import team.opentech.usher.pojo.cqe.SpaceCreateCommand;
import team.opentech.usher.pojo.cqe.UpdateSpaceInfoCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.pojo.entity.AiSpaceUserLink;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.pojo.event.CleanSpaceUserEvent;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.repository.AiSpaceUserLinkRepository;
import team.opentech.usher.repository.AiSubspaceConnectionPointRepository;
import team.opentech.usher.repository.AiSubspaceRepository;
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

    @Resource
    private AiSubspaceAssembler subspaceAssembler;

    @Resource
    private AiSubspaceRepository subspaceRepository;

    @Resource
    private AiSubspaceConnectionPointRepository connectionPointRepository;

    @Resource
    private UserFacade userFacade;

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
        AiSpace aiSpace = rep.find(command.getSpaceId());
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        aiSpace.addUser(userLinkRepository, command.getUserId(), command.getAdmin());
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeUserFromSpace(RemoveUserFromSpaceCommand command) {
        AiSpace aiSpace = rep.find(command.getSpaceId());
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        return aiSpace.removeUser(userLinkRepository, command.getUserId());
    }

    @Override
    public Boolean createSubSpace(CreateSubSpaceCommand command) {
        AiSubspace subspace = subspaceAssembler.toEntity(command);
        subspace.removeId();
        // 保存子空间
        subspace.saveSelf(subspaceRepository);
        // 保存连通点(如果有的话)
        subspace.createConnectionPoint(connectionPointRepository);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeSpace(RemoveSpaceCommand command) {
        AiSpace aiSpace = rep.find(command.getSpaceId());
        Asserts.assertTrue(aiSpace != null, "指定的独立空间不存在!");
        aiSpace.removeSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public void cleanSpaceUserEvent(CleanSpaceUserEvent event) {
        userLinkRepository.removeBySpaceId(event.getSpaceId());
    }

    @Override
    public List<AiSpaceDTO> findByOnlineUser(DefaultCQE blackQuery) {
        List<AiSpaceUserLink> userLinks = userLinkRepository.findByUserId(blackQuery.getUser().getId());
        List<Long> spaceIds = userLinks.stream().map(AiSpaceUserLink::spaceId).collect(Collectors.toList());
        List<AiSpace> aiSpaces = rep.find(spaceIds);
        return assem.listEntityToDTO(aiSpaces);
    }

    @Override
    public List<UserDTO> findUserBySpaceId(IdQuery query) {
        List<AiSpaceUserLink> userLinks = userLinkRepository.findBySpaceId(query.getId());
        List<Long> userIds = userLinks.stream().map(AiSpaceUserLink::userId).collect(Collectors.toList());
        return userFacade.findByUserIdList(userIds);
    }

    @Override
    public Boolean removeSubSpace(IdCommand command) {
        return subspaceRepository.remove(command.getId()) == 1;
    }

    @Override
    public Boolean changeSubSpace(ChangeSubSpaceCommand command) {
        AiSubspace aiSubspace = subspaceAssembler.toEntity(command);
        aiSubspace.onUpdate();
        aiSubspace.saveSelf(subspaceRepository);
        return Boolean.TRUE;
    }

    @Override
    public AiSubspaceDTO findSubSpaceById(IdQuery query) {
        AiSubspace aiSubspace = subspaceRepository.find(query.getId());
        return subspaceAssembler.toDTO(aiSubspace);
    }

    @Override
    public List<AiSubspaceDTO> findSubSpaceBySpaceId(FindSubSpaceBySpaceIdQuery query) {
        List<AiSubspace> bySpaceId = subspaceRepository.findBySpaceId(query.getSpaceId());
        for (AiSubspace subspace : bySpaceId) {
            subspace.fillConnectionPoint(connectionPointRepository);
        }
        return subspaceAssembler.listEntityToDTO(bySpaceId);
    }

    @Override
    public Boolean updateSpaceInfo(UpdateSpaceInfoCommand command) {
        Asserts.assertTrue(command.getSpaceId() != null, "修改时空间id不能为空");
        AiSpace aiSpace = rep.find(command.getSpaceId());
        aiSpace.changeName(command.getSpaceName());
        aiSpace.saveSelf(rep);
        return Boolean.TRUE;
    }
}
