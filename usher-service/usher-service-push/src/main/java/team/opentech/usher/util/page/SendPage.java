package team.opentech.usher.util.page;

import team.opentech.usher.assembler.PushPageMsgAssembler;
import team.opentech.usher.context.UsherContext;
import team.opentech.usher.dao.PushPageMsgDao;
import team.opentech.usher.pojo.DO.PushPageMsgDO;
import team.opentech.usher.pojo.DTO.PushPageMsgDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.util.SendPageBuild;
import team.opentech.usher.util.SpringUtil;

/**
 * 页面推送
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月29日 08时22分
 */
public class SendPage {

    public static Boolean send(Long userId, String title, String sendContent) {
        // 获取dao
        PushPageMsgDao bean = SpringUtil.getBean(PushPageMsgDao.class);
        PushPageMsgAssembler assembler = SpringUtil.getBean(PushPageMsgAssembler.class);
        // 获取要插入的bean
        PushPageMsgDTO sendPageEntity = SendPageBuild.buildSendPage(userId, title, sendContent);
        // 构造系统请求
        DefaultCQE request = new DefaultCQE();
        UserDTO user = new UserDTO();
        user.setId(UsherContext.ADMIN_USER_ID);
        request.setUser(user);
        PushPageMsgDO pushPageMsgDO = assembler.toDo(sendPageEntity);

        pushPageMsgDO.preInsert(request);
        //插入
        bean.insert(pushPageMsgDO);
        return Boolean.TRUE;
    }
}
