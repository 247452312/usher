package team.opentech.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.MysqlErrCodeEnum;
import team.opentech.usher.mysql.enums.MysqlHandlerStatusEnum;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.ErrResponse;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComQuitCommand extends AbstractMysqlCommand {

    public ComQuitCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    @Override
    public List<MysqlResponse> invoke() {
        MysqlContent.MYSQL_TCP_INFO.get().setStatus(MysqlHandlerStatusEnum.OVER);
        return Collections.singletonList(ErrResponse.build("?? 你竟然想关掉我? 脑子瓦特了?\n" + MysqlErrCodeEnum.EE_FAILED_PROCESSING_DIRECTIVE.getMsg()));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_QUIT;
    }

    @Override
    protected void load() {
        // 既然是quit 就没啥好load的了
    }
}
