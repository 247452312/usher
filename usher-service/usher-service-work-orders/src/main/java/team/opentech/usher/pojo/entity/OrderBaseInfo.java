package team.opentech.usher.pojo.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OrderBaseInfoDO;
import team.opentech.usher.pojo.DO.OrderBaseNodeFieldDO;
import team.opentech.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import team.opentech.usher.pojo.DO.OrderBaseNodeRouteDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.OrderBaseNodeFieldRepository;
import team.opentech.usher.repository.OrderBaseNodeRepository;
import team.opentech.usher.repository.OrderBaseNodeResultTypeRepository;
import team.opentech.usher.repository.OrderBaseNodeRouteRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时58分53秒
 */
public class OrderBaseInfo extends AbstractDoEntity<OrderBaseInfoDO> {

    private List<OrderBaseNode> nodes;

    @Default
    public OrderBaseInfo(OrderBaseInfoDO data) {
        super(data);
    }

    public OrderBaseInfo(Long id) {
        super(id, new OrderBaseInfoDO());
    }

    public List<OrderBaseNode> nodes() {
        return nodes;
    }

    /**
     * 填充节点
     *
     * @param nodeRepository
     */
    public void fillNoHiddenNode(OrderBaseNodeRepository nodeRepository) {
        if (nodes == null) {
            this.nodes = nodeRepository.findNoHiddenNodeById(getUnique().orElseThrow(Asserts::throwOptionalException));
        }
    }

    public void recursionFillNodeInfo(OrderBaseNodeFieldRepository fieldRepository, OrderBaseNodeRouteRepository routeRepository, OrderBaseNodeResultTypeRepository resultTypeRepository) {
        if (CollectionUtil.isEmpty(nodes)) {
            return;
        }
        fillNodeField(fieldRepository);
        fillNodeResultType(resultTypeRepository);
        fillNodeRoute(routeRepository);
    }

    public void fillNodeField(OrderBaseNodeFieldRepository fieldRepository) {
        List<OrderBaseNodeField> fields = fieldRepository.findNodeFieldByNodes(this.nodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList()));
        Map<Long, List<OrderBaseNodeField>> nodeIdFieldMap = fields.stream().collect(Collectors.groupingBy(t -> t.toData().map(OrderBaseNodeFieldDO::getBaseOrderId).orElseThrow(Asserts::throwOptionalException)));
        for (OrderBaseNode node : this.nodes) {
            Long id = node.getUnique().orElseThrow(Asserts::throwOptionalException);
            List<OrderBaseNodeField> fieldList = nodeIdFieldMap.get(id);
            node.fillFields(fieldList);
        }
    }

    public void fillNodeResultType(OrderBaseNodeResultTypeRepository resultTypeRepository) {
        List<OrderBaseNodeResultType> resultTypes = resultTypeRepository.findNodeResultTypeByNodes(this.nodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList()));
        Map<Long, List<OrderBaseNodeResultType>> nodeIdResultTypeMap = resultTypes.stream().collect(Collectors.groupingBy(t -> t.toData().map(OrderBaseNodeResultTypeDO::getBaseNodeId).orElseThrow(Asserts::throwOptionalException)));
        for (OrderBaseNode node : this.nodes) {
            Long id = node.getUnique().orElseThrow(Asserts::throwOptionalException);
            List<OrderBaseNodeResultType> orderBaseNodeResultTypes = nodeIdResultTypeMap.get(id);
            node.fillResultTypes(orderBaseNodeResultTypes);
        }
    }

    public void fillNodeRoute(OrderBaseNodeRouteRepository routeRepository) {
        List<OrderBaseNodeRoute> routes = routeRepository.findNodeRouteByNodes(this.nodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList()));
        Map<Long, List<OrderBaseNodeRoute>> nodeIdRouteMap = routes.stream().collect(Collectors.groupingBy(t -> t.toData().map(OrderBaseNodeRouteDO::getPrevNodeId).orElseThrow(Asserts::throwOptionalException)));
        for (OrderBaseNode node : this.nodes) {
            Long id = node.getUnique().orElseThrow(Asserts::throwOptionalException);
            List<OrderBaseNodeRoute> orderBaseNodeRoutes = nodeIdRouteMap.get(id);
            node.fillRoutes(orderBaseNodeRoutes);
        }

    }

    public void forceFillNode(List<OrderBaseNode> nodes) {
        this.nodes = nodes;
    }
}
