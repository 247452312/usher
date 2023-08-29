package team.opentech.usher.mysql.pojo.cqe.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import team.opentech.usher.exception.AssertException;
import team.opentech.usher.mysql.decode.Proto;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.ErrResponse;
import team.opentech.usher.mysql.pojo.response.impl.OkResponse;
import team.opentech.usher.mysql.pojo.response.impl.ResultSetResponse;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.plan.MysqlPlan;
import team.opentech.usher.plan.PlanInvoker;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.StringUtil;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComQueryCommand extends MysqlSqlCommand {

    private String completeSql;

    public ComQueryCommand(MysqlThisRequestInfo mysqlThisRequestInfo, String sql) {
        this(mysqlThisRequestInfo);
        this.completeSql = sql;
    }

    public ComQueryCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() throws Exception {
        if (StringUtil.isEmpty(completeSql)) {
            return Collections.singletonList(ErrResponse.build("sql语句不能为空"));
        }
        LogUtil.info("mysql协议sql执行了:" + completeSql);
        String[] split = completeSql.split(";");
        List<MysqlResponse> result = new ArrayList<>();
        for (String sql : split) {
            // 解析sql为执行计划
            List<MysqlPlan> mysqlPlans = MysqlUtil.analysisSqlToPlan(sql);
            // 执行计划为空, 返回执行成功,无信息
            if (CollectionUtil.isEmpty(mysqlPlans)) {
                List<OkResponse> okResponses = Collections.singletonList(new OkResponse(SqlTypeEnum.NULL));
                result.addAll(okResponses);
                continue;
            }

            NodeInvokeResult execute;
            try {
                execute = new PlanInvoker(mysqlPlans).execute();
            } catch (AssertException e) {
                LogUtil.error(this, e, "sql:" + sql + "\n");
                throw e;
            }

            // 如果没有结果, 说明不是一个常规的查询语句,返回ok即可,如果报错,则在外部已经进行了try,catch
            if (CollectionUtil.isEmpty(execute.getFieldInfos())) {
                result.add(new OkResponse(SqlTypeEnum.NULL));
                continue;
            }
            result.add(new ResultSetResponse(execute.getFieldInfos(), execute.getResult()));
        }

        LogUtil.info("mysql协议sql回应:" + result.stream().map(MysqlResponse::toResponseStr).collect(Collectors.joining()));
        return result;
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_QUERY;
    }

    @Override
    protected void load() {
        byte[] mysqlBytes = mysqlThisRequestInfo.getMysqlBytes();
        Proto proto = new Proto(mysqlBytes, 5);
        this.completeSql = proto.get_null_str();
    }
}
