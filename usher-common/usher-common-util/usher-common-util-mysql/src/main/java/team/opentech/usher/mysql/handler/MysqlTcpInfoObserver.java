package team.opentech.usher.mysql.handler;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月05日 21时49分
 */
public interface MysqlTcpInfoObserver {


    /**
     * 获取mysql此次请求的信息
     *
     * @return
     */
    MysqlThisRequestInfo getMysqlThisRequestInfo();

}