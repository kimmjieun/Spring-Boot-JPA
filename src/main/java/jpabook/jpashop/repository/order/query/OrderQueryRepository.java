package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    /**
     * 컬렉션은 별도로 조회
     * Query: 루트 1번, 컬렉션 N번
     * 단건 조회에서 많이 사용하는 방식
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        // 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders(); //쿼리 1번 -> n

        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행)
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); // 쿼리 n번 -> n = n+1문제
            o.setOrderItems(orderItems);
        });
        return result ;
    }

    /**
     * 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회
     */
    private List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name,o.orderDate,o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d" , OrderQueryDto.class)
                .getResultList();
    }

    /**
     * 1:N 관계인 orderItems 조회
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name,oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id =: orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();

    }


    /**
     * 최적화
     * Query : 루트 1번, 컬렉션 1번
     * 데이터를 한꺼번에 처리할 때 많이 사용
     */
    public List<OrderQueryDto> findAllByDto_optimization() {
        // 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders();

        System.out.println("result"+result);

        // orderItem 컬렉션을 MAP 한방에 조회
        Map<Long,List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        // findOrderItemMap 으로 쿼리를 한번 날리고 맵에서 메모리에서 데이터를 가져옴

        // 루프를 돌면서 컬렉션 추가 (추가 쿼리 실행 x)
        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result){
        return result.stream()
                .map(o->o.getOrderId())
                .collect(Collectors.toList()); // orderId 리스트를 뽑아라
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds){
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds) // 파라미터 인자를 넣어
                .getResultList();
        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

}