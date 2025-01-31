package team.opentech.usher.util;

import team.opentech.usher.pojo.DTO.PushPageMsgDTO;

/**
 * 发送页面
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月29日 08时18分
 */
public class SendPageBuild {

    /**
     * 创建一个页面推送
     *
     * @param userId  用户id
     * @param title   标题
     * @param content 内容
     *
     * @return 页面推送
     */
    public static PushPageMsgDTO buildSendPage(Long userId, String title, String content) {
        PushPageMsgDTO sendPageEntity = new PushPageMsgDTO();
        sendPageEntity.setContent(content);
        sendPageEntity.setTitle(title);
        sendPageEntity.setUserId(userId);
        sendPageEntity.setView(false);
        return sendPageEntity;
    }
}
