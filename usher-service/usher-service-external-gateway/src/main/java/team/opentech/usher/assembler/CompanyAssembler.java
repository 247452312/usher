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


    public MUserInfo toMUser(CompanyDTO user) {

        MUserInfo mUserInfo = new MUserInfo();
        mUserInfo.setHost("%");
        mUserInfo.setUser(user.getAk());
        mUserInfo.setSelectPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setInsertPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setUpdatePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setDeletePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreatePriv(MysqlContent.MYSQL_NO);
        mUserInfo.setDropPiv(MysqlContent.MYSQL_NO);
        mUserInfo.setReloadPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setShutdownPriv(MysqlContent.MYSQL_NO);
        mUserInfo.setProcessPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setFilePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setGrantPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setReferencesPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setIndexPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setAlterPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setShowDbPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setSuperPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreateTmpTablePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setLockTablesPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setExecutePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setReplSlavePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setReplClientPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreateViewPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setShowViewPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreateRoutinePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setAlterRoutinePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreateUserPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setEventPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setTriggerPriv(MysqlContent.MYSQL_YES);
        mUserInfo.setCreateTablespacePriv(MysqlContent.MYSQL_YES);
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
        mUserInfo.setPasswordExpired(MysqlContent.MYSQL_NO);
        mUserInfo.setPasswordLastChanged(null);
        mUserInfo.setPasswordLifetime(null);
        mUserInfo.setAccountLocked(MysqlContent.MYSQL_NO);
        mUserInfo.setCreateRolePriv(MysqlContent.MYSQL_YES);
        mUserInfo.setDropRolePriv(MysqlContent.MYSQL_YES);
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
        iUserPrivilegesInfo.setIsGrantable(MysqlContent.MYSQL_NO);
        return iUserPrivilegesInfo;
    }
}
