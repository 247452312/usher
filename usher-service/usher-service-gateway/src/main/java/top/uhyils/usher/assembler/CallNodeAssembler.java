package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.pojo.DTO.ISchemaPrivilegesInfo;
import top.uhyils.usher.pojo.DO.CallNodeDO;
import top.uhyils.usher.pojo.DTO.CallNodeDTO;
import top.uhyils.usher.pojo.entity.CallNode;

/**
 * 调用节点表, 真正调用的节点(CallNode)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CallNodeAssembler extends AbstractAssembler<CallNodeDO, CallNode, CallNodeDTO> {


    public ISchemaPrivilegesInfo toISchemaPrivilegesInfo(CallNodeDTO callNodeDTO) {
        ISchemaPrivilegesInfo iSchemaPrivilegesInfo = new ISchemaPrivilegesInfo();
        iSchemaPrivilegesInfo.setGrantee(callNodeDTO.getUrl().split("/")[0] + "@%");
        iSchemaPrivilegesInfo.setTableCatalog(MysqlContent.CATALOG_NAME);
        iSchemaPrivilegesInfo.setPrivilegeType("SELECT");
        iSchemaPrivilegesInfo.setIsGrantable(MysqlContent.MYSQL_NO);
        iSchemaPrivilegesInfo.setTableSchema(callNodeDTO.getUrl().split("/")[0]);
        return iSchemaPrivilegesInfo;
    }

}
