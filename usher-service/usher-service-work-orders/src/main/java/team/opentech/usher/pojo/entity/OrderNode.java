package team.opentech.usher.pojo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.assembler.OrderNodeResultTypeAssembler;
import team.opentech.usher.assembler.OrderNodeRouteAssembler;
import team.opentech.usher.builder.OrderNodeResultTypeBuilder;
import team.opentech.usher.builder.OrderNodeRouteBuilder;
import team.opentech.usher.enums.OrderNodeResultTypeEnum;
import team.opentech.usher.enums.OrderNodeRunTypeEnum;
import team.opentech.usher.enums.OrderNodeStatusEnum;
import team.opentech.usher.facade.PushFacade;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DO.OrderNodeFieldDO;
import team.opentech.usher.pojo.DO.OrderNodeResultTypeDO;
import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import team.opentech.usher.pojo.DO.base.BaseIdDO;
import team.opentech.usher.pojo.DTO.OrderNodeResultTypeDTO;
import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.pojo.IdMapping;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.OrderInfoRepository;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.repository.OrderNodeFieldValueRepository;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.BeanUtil;
import team.opentech.usher.util.CollectionUtil;

/**
 * 工单节点样例表(OrderNode)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分20秒
 */
public class OrderNode extends AbstractDoEntity<OrderNodeDO> {

    /**
     * 节点属性
     */
    private List<OrderNodeField> fields;

    /**
     * 节点结果类型
     */
    private List<OrderNodeResultType> resultTypes;

    /**
     * 节点结果路由
     */
    private List<OrderNodeRoute> routes;

    @Default
    public OrderNode(OrderNodeDO data) {
        super(data);
    }

    public OrderNode(Long id) {
        super(id, new OrderNodeDO());
    }

    public List<OrderNodeField> fields() {
        return fields;
    }

    /**
     * 保存自身
     *
     * @param nodeRepository 节点
     */
    public IdMapping saveSelf(OrderNodeRepository nodeRepository) {
        Long oldId = this.getUnique().orElseThrow(Asserts::throwOptionalException);
        this.setUnique(null);
        toData().orElseThrow(Asserts::throwOptionalException).setId(null);
        nodeRepository.save(this);
        Long newId = this.getUnique().orElseThrow(Asserts::throwOptionalException);
        return IdMapping.build(newId, oldId);
    }

    public void changeAndSaveFieldNodeId(IdMapping idMappings, OrderNodeFieldRepository fieldRepository) {
        Map<Long, Long> trans = new HashMap<>(1);
        trans.put(idMappings.getOldId(), idMappings.getNewId());
        changeAndSaveFieldNodeId(trans, fieldRepository);
    }

    public void changeAndSaveFieldNodeId(Map<Long, Long> idMappings, OrderNodeFieldRepository fieldRepository) {
        if (CollectionUtil.isEmpty(fields)) {
            return;
        }
        for (OrderNodeField field : fields) {
            OrderNodeFieldDO orderNodeFieldDO = field.toData().orElseThrow(Asserts::throwOptionalException);
            orderNodeFieldDO.setBaseOrderNodeId(idMappings.get(orderNodeFieldDO.getBaseOrderNodeId()));
            field.setUnique(null);
            orderNodeFieldDO.setId(null);
            field.setUnique(fieldRepository.save(field));
        }
    }

    public void changeAndSaveResultTypeAndRoute(IdMapping idMappings, OrderNodeRouteRepository routeRepository, OrderNodeResultTypeRepository resultTypeRepository) {
        Map<Long, Long> trans = new HashMap<>(1);
        trans.put(idMappings.getOldId(), idMappings.getNewId());
        changeAndSaveResultTypeAndRoute(trans, routeRepository, resultTypeRepository);
    }

    public void changeAndSaveResultTypeAndRoute(Map<Long, Long> idMappings, OrderNodeRouteRepository routeRepository, OrderNodeResultTypeRepository resultTypeRepository) {
        if (CollectionUtil.isEmpty(resultTypes) || CollectionUtil.isEmpty(routes)) {
            return;
        }
        for (OrderNodeResultType resultType : resultTypes) {
            Long unique = resultType.getUnique().orElseThrow(Asserts::throwOptionalException);
            OrderNodeResultTypeDO orderNodeResultTypeDO = resultType.toData().orElseThrow(Asserts::throwOptionalException);
            orderNodeResultTypeDO.setBaseNodeId(idMappings.get(orderNodeResultTypeDO.getBaseNodeId()));
            orderNodeResultTypeDO.setId(null);
            resultType.setUnique(null);
            resultType.setUnique(resultTypeRepository.save(resultType));
            idMappings.put(unique, unique);
        }

        for (OrderNodeRoute route : routes) {
            OrderNodeRouteDO orderNodeRouteDO = route.toData().orElseThrow(Asserts::throwOptionalException);
            orderNodeRouteDO.setNextNodeId(idMappings.get(orderNodeRouteDO.getNextNodeId()));
            orderNodeRouteDO.setPrevNodeId(idMappings.get(orderNodeRouteDO.getPrevNodeId()));
            orderNodeRouteDO.setResultId(idMappings.get(orderNodeRouteDO.getResultId()));
            orderNodeRouteDO.setId(null);
            route.setUnique(null);
            route.setUnique(routeRepository.save(route));
        }
    }

    public OrderNode copy() {
        OrderNodeDO dO = new OrderNodeDO();
        BeanUtil.copyProperties(data, dO);
        OrderNode orderNode = new OrderNode(dO);
        orderNode.forceFillInfo(fields, resultTypes, routes);
        return orderNode;
    }

    public void forceFillInfo(List<OrderNodeField> fields, List<OrderNodeResultType> resultTypes, List<OrderNodeRoute> routes) {
        this.fields = fields;
        this.resultTypes = resultTypes;
        this.routes = routes;
    }

    public OrderNodeResultType createResultByTrans(OrderNodeResultTypeAssembler typeAssembler) {
        Long unique = getUnique().orElseThrow(Asserts::throwOptionalException);
        OrderNodeResultTypeDTO transResult = OrderNodeResultTypeBuilder.build(unique, "转交");
        return typeAssembler.toEntity(transResult);

    }

    public void addTestResult(OrderNodeResultType result, OrderNodeRepository nodeRepository) {
        data.setResultId(result.toData().map(BaseIdDO::getId).orElseThrow(Asserts::throwOptionalException));
        data.setResultType(OrderNodeResultTypeEnum.TRANSFER.getCode());
        onUpdate();
        nodeRepository.save(this);
    }

    public OrderNodeRoute createRoute(OrderNodeRouteRepository routeRepository, OrderNodeRouteAssembler routeAssembler, Long resultId, OrderNode lastNode) {
        OrderNodeRouteDTO build = OrderNodeRouteBuilder.build(getUnique().orElseThrow(Asserts::throwOptionalException), resultId, lastNode.getUnique().orElseThrow(Asserts::throwOptionalException));
        OrderNodeRoute orderNodeRoute = routeAssembler.toEntity(build);
        routeRepository.save(orderNodeRoute);
        return orderNodeRoute;
    }

    public void assertAllow() {
        OrderNodeStatusEnum statusEnum = OrderNodeStatusEnum.parse(data.getStatus());
        Asserts.assertTrue(statusEnum == OrderNodeStatusEnum.IN_START, "节点处于不能被处理状态:" + statusEnum.getName());

    }

    public void fillField(OrderNodeFieldRepository fieldRepository) {
        if (this.fields == null) {
            this.fields = fieldRepository.findByNodeId(getUnique().orElseThrow(Asserts::throwOptionalException));
        }


    }

    public void assertAndSaveFieldValues(OrderNodeFieldValueRepository fieldValueRepository, Map<Long, Object> orderNodeFieldValueMap) {
        Asserts.assertTrue(this.fields != null, "节点属性本身信息不存在!");

        for (OrderNodeField field : fields) {
            Long id = field.getUnique().orElseThrow(Asserts::throwOptionalException);
            Asserts.assertTrue(orderNodeFieldValueMap.containsKey(id), field.toData().map(OrderNodeFieldDO::getName).orElseThrow(Asserts::throwOptionalException) + " 未填写");
            String realValue = String.valueOf(orderNodeFieldValueMap.get(id));
            OrderNodeFieldValue orderNodeFieldValue = new OrderNodeFieldValue(field, realValue);
            orderNodeFieldValue.assertSelf();
            fieldValueRepository.save(orderNodeFieldValue);

        }
    }

    public void successAndClose(OrderNodeRepository rep, Long resultId, String suggest) {
        data.setStatus(OrderNodeStatusEnum.OVER.getCode());
        data.setResultType(OrderNodeResultTypeEnum.SUCCESS.getCode());
        data.setResultId(resultId);
        data.setSuggest(suggest);
        onUpdate();
        rep.save(this);
    }

    public OrderNode nextOrder(OrderNodeRepository rep) {
        Asserts.assertTrue(data.getResultId() != null, "没有结果,不能路由到下一个节点");
        return rep.findNext(this);
    }

    public void start(OrderNodeRepository rep) {
        changeStatus(rep, OrderNodeStatusEnum.WAIT_STATUS);
    }

    public void changeStatus(OrderNodeRepository rep, OrderNodeStatusEnum status) {
        data.setStatus(status.getCode());
        onUpdate();
        rep.save(this);
    }

    public void checkAuto(PushFacade pushFacade, OrderNode pervOrder) {
        Integer runType = data.getRunType();
        if (OrderNodeRunTypeEnum.AUTO == OrderNodeRunTypeEnum.parse(runType)) {
            // 通知自动处理模块
            pushFacade.noticeAutoNodeDeal(this, pervOrder);
        }
    }

    public void transfer(OrderNodeRepository rep) {
        changeStatus(rep, OrderNodeStatusEnum.TRANSFER);
    }

    public OrderInfo findBaseInfo(OrderInfoRepository infoRepository) {
        return infoRepository.find(data.getBaseInfoId());
    }
}
