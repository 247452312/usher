package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.DTO.IUserPrivilegesInfo;
import team.opentech.usher.mysql.pojo.DTO.MUserInfo;
import team.opentech.usher.pojo.DO.CompanyDO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.entity.Company;

/**
 * 厂商表(Company)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CompanyAssembler extends AbstractAssembler<CompanyDO, Company, CompanyDTO> {

    private static final String MYSQL_YES = "Y";

    private static final String MYSQL_NO = "N";

    public MUserInfo toMUser(CompanyDTO user) {

        MUserInfo mUserInfo = new MUserInfo();
        mUserInfo.setHost("%");
        mUserInfo.setUser(user.getAk());
        mUserInfo.setSelectPriv(MYSQL_YES);
        mUserInfo.setInsertPriv(MYSQL_YES);
        mUserInfo.setUpdatePriv(MYSQL_YES);
        mUserInfo.setDeletePriv(MYSQL_YES);
        mUserInfo.setCreatePriv(MYSQL_NO);
        mUserInfo.setDropPiv(MYSQL_NO);
        mUserInfo.setReloadPriv(MYSQL_YES);
        mUserInfo.setShutdownPriv(MYSQL_NO);
        mUserInfo.setProcessPriv(MYSQL_YES);
        mUserInfo.setFilePriv(MYSQL_YES);
        mUserInfo.setGrantPriv(MYSQL_YES);
        mUserInfo.setReferencesPriv(MYSQL_YES);
        mUserInfo.setIndexPriv(MYSQL_YES);
        mUserInfo.setAlterPriv(MYSQL_YES);
        mUserInfo.setShowDbPriv(MYSQL_YES);
        mUserInfo.setSuperPriv(MYSQL_YES);
        mUserInfo.setCreateTmpTablePriv(MYSQL_YES);
        mUserInfo.setLockTablesPriv(MYSQL_YES);
        mUserInfo.setExecutePriv(MYSQL_YES);
        mUserInfo.setReplSlavePriv(MYSQL_YES);
        mUserInfo.setReplClientPriv(MYSQL_YES);
        mUserInfo.setCreateViewPriv(MYSQL_YES);
        mUserInfo.setShowViewPriv(MYSQL_YES);
        mUserInfo.setCreateRoutinePriv(MYSQL_YES);
        mUserInfo.setAlterRoutinePriv(MYSQL_YES);
        mUserInfo.setCreateUserPriv(MYSQL_YES);
        mUserInfo.setEventPriv(MYSQL_YES);
        mUserInfo.setTriggerPriv(MYSQL_YES);
        mUserInfo.setCreateTablespacePriv(MYSQL_YES);
        mUserInfo.setSslType(null);
        mUserInfo.setSslCipher(null);
        mUserInfo.setX509Issuer(null);
        mUserInfo.setX509Subject(null);
        mUserInfo.setMaxQuestions(0);
        mUserInfo.setMaxUpdates(0);
        mUserInfo.setMaxConnections(0);
        mUserInfo.setMaxUserConnections(0);
        mUserInfo.setPlugin(null);
        mUserInfo.setAuthenticationString(user.getSk());
        mUserInfo.setPasswordExpired(MYSQL_NO);
        mUserInfo.setPasswordLastChanged(null);
        mUserInfo.setPasswordLifetime(null);
        mUserInfo.setAccountLocked(MYSQL_NO);
        mUserInfo.setCreateRolePriv(MYSQL_YES);
        mUserInfo.setDropRolePriv(MYSQL_YES);
        mUserInfo.setPasswordReuseHistory(null);
        mUserInfo.setPasswordReuseTime(null);
        mUserInfo.setPasswordRequireCurrent(null);
        mUserInfo.setUserAttributes(null);
        return mUserInfo;
    }

    public IUserPrivilegesInfo toIUserPrivileges(CompanyDTO user) {
        IUserPrivilegesInfo iUserPrivilegesInfo = new IUserPrivilegesInfo();
        iUserPrivilegesInfo.setGrantee(user.getAk() + "@%");
        iUserPrivilegesInfo.setTableCatalog(MysqlContent.CATALOG_NAME);
        iUserPrivilegesInfo.setPrivilegeType("SELECT");
        iUserPrivilegesInfo.setIsGrantable(MYSQL_NO);
        return iUserPrivilegesInfo;
    }
}
