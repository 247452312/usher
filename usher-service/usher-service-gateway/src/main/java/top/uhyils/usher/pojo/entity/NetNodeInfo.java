package top.uhyils.usher.pojo.entity;

import java.util.List;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.assembler.CompanyPowerAssembler;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.CompanyPowerRepository;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.util.SpringUtil;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public class NetNodeInfo extends AbstractDoEntity<NetNodeInfoDO> {

    /**
     * 详情, 当前节点对应sql类型 例如 query,update等
     */
    private List<NetNodeInfoDetail> details;

    @Default
    public NetNodeInfo(NetNodeInfoDO data) {
        super(data);
    }

    public NetNodeInfo(Long id) {
        super(id, new NetNodeInfoDO());
    }

    /**
     * 保存到指定公司id
     *
     * @param rep
     * @param companyId
     */
    public void saveSelfByCompanyId(NetNodeInfoRepository rep, List<NetNodeInfoDetail> details, Long companyId) {
        saveSelf(rep);
        NetNodeInfoDetailRepository netNodeInfoDetailRepository = SpringUtil.getBean(NetNodeInfoDetailRepository.class);
        for (NetNodeInfoDetail detail : details) {
            detail.fillNodeId(this.unique);
        }
        netNodeInfoDetailRepository.save(details);
        CompanyPowerRepository companyPowerRepository = SpringUtil.getBean(CompanyPowerRepository.class);
        CompanyPowerAssembler companyPowerAssembler = SpringUtil.getBean(CompanyPowerAssembler.class);
        // todo 这里状态直接是审核完成,之后需要添加审核流程
        CompanyPower entity = companyPowerAssembler.toEntity(this.unique, companyId, 1);
        entity.saveSelf(companyPowerRepository);
    }


    public List<NetNodeInfoDetail> details() {
        if (details != null) {
            return details;
        }
        NetNodeInfoDetailRepository detailRepository = SpringUtil.getBean(NetNodeInfoDetailRepository.class);
        this.details = detailRepository.findByNodeId(this.unique);
        return this.details;
    }

}
