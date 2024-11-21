package team.opentech.usher.pojo.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.base.AbstractEntity;
import team.opentech.usher.pojo.entity.type.Identifiers;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 11时57分
 */
public class OrderNodeList extends AbstractEntity<Identifiers> {

    private final List<OrderNode> orderNodes;

    public OrderNodeList(List<OrderNode> orderNodes) {
        this.orderNodes = orderNodes;
        setUnique(new Identifiers(orderNodes.stream().map(AbstractEntity::getUnique).map(t -> t.orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList())));
    }

    public void compareAndSaveDealUser(Map<Long, Long> dealUserIds) {
        Asserts.assertTrue(dealUserIds != null, "处理人不能为空");
        for (OrderNode orderNode : orderNodes) {
            Long id = orderNode.getUnique().orElseThrow(Asserts::throwOptionalException);
            Long dealUser = dealUserIds.get(id);
            if (dealUser == null) {
                continue;
            }
            orderNode.toData().orElseThrow(Asserts::throwOptionalException).setRunDealUserId(dealUser);
            orderNode.onUpdate();
        }
    }

    public void compareAndSaveNoticeUser(Map<Long, Long> noticeUserIds) {
        Asserts.assertTrue(noticeUserIds != null, "通知人不能为空");
        for (OrderNode orderNode : orderNodes) {
            Long id = orderNode.getUnique().orElseThrow(Asserts::throwOptionalException);
            Long noticeUser = noticeUserIds.get(id);
            if (noticeUser == null) {
                continue;
            }
            orderNode.toData().orElseThrow(Asserts::throwOptionalException).setNoticeUserId(noticeUser);
            orderNode.onUpdate();
        }

    }

    public void saveAll(OrderNodeRepository nodeRepository) {
        nodeRepository.save(orderNodes);
    }

    public void delFields(OrderNodeFieldRepository fieldRepository) {
        List<Long> collect = orderNodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList());
        List<OrderNodeField> byNodeIds = fieldRepository.findByNodeIds(collect);
        List<OrderNodeField> fields = byNodeIds.stream().peek(t -> t.toData().orElseThrow(Asserts::throwOptionalException).setDeleteFlag(true)).peek(AbstractDoEntity::onUpdate).collect(Collectors.toList());
        fieldRepository.save(fields);
    }

    public void delRoutes(OrderNodeRouteRepository routeRepository) {
        List<Long> collect = orderNodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList());
        List<OrderNodeRoute> routes = routeRepository.findByNodeIds(collect);
        List<OrderNodeRoute> route = routes.stream().peek(t -> t.toData().orElseThrow(Asserts::throwOptionalException).setDeleteFlag(true)).peek(AbstractDoEntity::onUpdate).collect(Collectors.toList());
        routeRepository.save(route);
    }

    public void delResultType(OrderNodeResultTypeRepository resultTypeRepository) {
        List<Long> collect = orderNodes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList());
        List<OrderNodeResultType> routes = resultTypeRepository.findByNodeIds(collect);
        List<OrderNodeResultType> route = routes.stream().peek(t -> t.toData().orElseThrow(Asserts::throwOptionalException).setDeleteFlag(true)).peek(AbstractDoEntity::onUpdate).collect(Collectors.toList());
        resultTypeRepository.save(route);
    }

    public void delSelf(OrderNodeRepository rep) {
        Identifiers unique = getUnique().orElseThrow(Asserts::throwOptionalException);
        List<Long> ids = unique.getIds();
        rep.removeByIds(ids);
    }
}
