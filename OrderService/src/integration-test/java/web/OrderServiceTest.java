package web;

import it.uniroma3.domain.OrderService;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@SpringBootTest
@EmbeddedKafka
public class OrderServiceTest {
    private static final String TOPIC = "order-service-channel";

    @Autowired
    private OrderService orderService;
    @ClassRule
    public static EmbeddedKafkaRule embededKafka = new EmbeddedKafkaRule(1, false, "order-service-channel");


    @Test
    public void test(){
        //KafkaTestUtils.getSingleRecord()
    }
}