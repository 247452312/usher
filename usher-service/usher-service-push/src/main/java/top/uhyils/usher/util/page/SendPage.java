package top.uhyils.usher.util.page;

import top.uhyils.usher.assembler.PushPageMsgAssembler;
import top.uhyils.usher.context.UsherContext;
import top.uhyils.usher.dao.PushPageMsgDao;
import top.uhyils.usher.pojo.DO.PushPageMsgDO;
import top.uhyils.usher.pojo.DTO.PushPageMsgDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.util.SendPageBuild;
import top.uhyils.usher.util.SpringUtil;

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
