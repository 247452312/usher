package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.assembler.AiSpaceUserLinkAssembler;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.event.SpaceRemoveParentEvent;
import team.opentech.usher.repository.AiSpaceUserLinkRepository;
import team.opentech.usher.repository.base.BaseEntityRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.SpringUtil;

/**
 * 独立空间(AiSpace)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpace extends AbstractDoEntity<AiSpaceDO> {

    @Default
    public AiSpace(AiSpaceDO data) {
        super(data);
    }

    public AiSpace(Long id) {
        super(id, new AiSpaceDO());
    }

    /**
     * 添加人员
     *
     * @param user
     */
    public void addUser(AiSpaceUserLinkRepository userLinkRepository, UserDTO user, Boolean isAdmin) {
        Long userId = user.getId();
        checkAddUserRepeat(userLinkRepository, userId, isAdmin);
        AiSpaceUserLinkAssembler spaceUserLinkAssembler = SpringUtil.getBean(AiSpaceUserLinkAssembler.class);
        AiSpaceUserLink aiSpaceUserLink = spaceUserLinkAssembler.toEntity(userId, isAdmin, this);
        aiSpaceUserLink.saveSelf(userLinkRepository);
    }

    /**
     * 添加人员
     *
     * @param userId
     */
    public void addUser(AiSpaceUserLinkRepository userLinkRepository, Long userId, Boolean isAdmin) {
        checkAddUserRepeat(userLinkRepository, userId, isAdmin);
        AiSpaceUserLinkAssembler spaceUserLinkAssembler = SpringUtil.getBean(AiSpaceUserLinkAssembler.class);
        AiSpaceUserLink aiSpaceUserLink = spaceUserLinkAssembler.toEntity(userId, isAdmin, this);
        aiSpaceUserLink.saveSelf(userLinkRepository);
    }

    /**
     * 移除人员
     *
     * @param userId 用户id
     */
    public Boolean removeUser(AiSpaceUserLinkRepository userLinkRepository, Long userId) {
        checkUserPower(UserInfoHelper.doGet());
        return userLinkRepository.removeUser(unique.getId(), userId);
    }

    /**
     * 删除自身
     *
     * @return
     */
    @Override
    public <EN extends AbstractDoEntity<AiSpaceDO>> void removeSelf(BaseEntityRepository<AiSpaceDO, EN> repository) {
        // 判断登录用户是否是管理员
        checkUserPower(UserInfoHelper.doGet());
        Bus.single().commitAndPush(new SpaceRemoveParentEvent(this));
        super.removeSelf(repository);
    }

    /**
     * 检查指定角色是否是管理员
     *
     * @param userDTO
     */
    private void checkUserPower(UserDTO userDTO) {
        AiSpaceUserLinkRepository spaceUserLinkRepository = SpringUtil.getBean(AiSpaceUserLinkRepository.class);
        Asserts.assertTrue(spaceUserLinkRepository.isAdmin(userDTO.getId(), unique.getId()), "当前用户不是独立空间的管理员");

    }

    /**
     * 检查将要添加的用户是否已存在
     *
     * @param userId  用户信息
     * @param isAdmin 是否是管理员
     */
    private void checkAddUserRepeat(AiSpaceUserLinkRepository userLinkRepository, Long userId, Boolean isAdmin) {
        Asserts.assertTrue(!(userLinkRepository.haveAdmin(unique.getId()) && isAdmin), "已存在管理员,不能再次设置管理员");
        Asserts.assertTrue(!userLinkRepository.checkUsher(userId, unique.getId()), "该用户在当前独立空间下已存在,请不要重复添加");
    }
}
