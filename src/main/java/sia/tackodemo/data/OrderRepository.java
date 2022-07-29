package sia.tackodemo.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sia.tackodemo.domain.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByDeliveryZip(String deliveryZip);

    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip,
                                                          Date startDate,
                                                          Date endDate);

    @Query("Order o where o.deliveryCity='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();

}
