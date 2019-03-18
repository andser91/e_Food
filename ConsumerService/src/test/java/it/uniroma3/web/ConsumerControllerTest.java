package it.uniroma3.web;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class ConsumerControllerTest {
    @InjectMocks
    ConsumerController consumerController;

    @Mock
    ConsumerService consumerService;

    private static Long CONSUMER_ID_1 = 5L;
    private static Long CONSUMER_ID_2 = 7L;
    private static Long CONSUMER_ID_NOT_EXISTING = 6L;
    private static String CONSUMER_FIRST_NAME_1 = "John";
    private static String CONSUMER_LAST_NAME_1 = "Smith";
    private static String CONSUMER_FIRST_NAME_2 = "Mary";
    private static String CONSUMER_LAST_NAME_2 = "Scott";

    private List<Consumer> consumers ;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        consumers = new ArrayList<Consumer>();
        Consumer consumer1 = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
        consumer1.setId(CONSUMER_ID_1);
        Consumer consumer2 = new Consumer(CONSUMER_FIRST_NAME_2, CONSUMER_LAST_NAME_2);
        consumer2.setId(CONSUMER_ID_2);
        consumers.add(consumer1);
        consumers.add(consumer2);
    }

    @Test
    public void postTest(){
        //verifica operazione post

        //configura il ConsumerService.create
        when(consumerService.create(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                    consumer.setId(CONSUMER_ID_1);
                    return consumer;
                });
        //invoca operazione post
        CreateConsumerRequest request = new CreateConsumerRequest();
        request.setFirstName(CONSUMER_FIRST_NAME_1);
        request.setLastName(CONSUMER_LAST_NAME_1);
        CreateConsumerResponse response = consumerController.newConsumer(request);

        //verifica che il servizio sia stato invocato
        verify(consumerService).create(same(CONSUMER_FIRST_NAME_1),same(CONSUMER_LAST_NAME_1));

        assertThat(response.getConsumerId()).isEqualTo(CONSUMER_ID_1);

    }

    @Test
    public void getConsumerTest(){
        when(consumerService.findById(CONSUMER_ID_1))
                .then(invocation -> {
                   Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                   consumer.setId(CONSUMER_ID_1);
                   return consumer;
                });

        ResponseEntity<GetConsumerResponse> consumerResponseEntity = consumerController.findById(CONSUMER_ID_1);
         verify(consumerService).findById(same(CONSUMER_ID_1));

        assertThat(consumerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetConsumerResponse getConsumersResponse =consumerResponseEntity.getBody();
        assertThat(getConsumersResponse.getConsumerId()).isEqualTo(CONSUMER_ID_1);
        assertThat(getConsumersResponse.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_1);
        assertThat(getConsumersResponse.getLastName()).isEqualTo(CONSUMER_LAST_NAME_1);
    }
    @Test
    public void getConsumerNotFoundTest(){
        when(consumerService.findById(CONSUMER_ID_1))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                    consumer.setId(CONSUMER_ID_1);
                    return Optional.of(consumer);
                });

        ResponseEntity<GetConsumerResponse> consumerResponseEntity = consumerController.findById(CONSUMER_ID_NOT_EXISTING);
        verify(consumerService).findById(CONSUMER_ID_NOT_EXISTING);

        assertThat(consumerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        GetConsumerResponse getConsumersResponse =consumerResponseEntity.getBody();
        assertThat(getConsumersResponse).isNull();
    }

    @Test
    public void getAllConsumersTest(){
        when(consumerService.findAll())
                .then(invocation -> {
                    return consumers;
                });

        ResponseEntity<GetConsumersResponse> response = consumerController.findAll();
        //verifica che il servi<io findAll sia stato invocato
        verify(consumerService).findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetConsumersResponse getConsumersResponse = response.getBody();
        List<GetConsumerResponse> consumers = getConsumersResponse.getConsumers();
        assertThat(consumers.get(0).getConsumerId()).isEqualTo(CONSUMER_ID_1);
        assertThat(consumers.get(0).getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_1);
        assertThat(consumers.get(0).getLastName()).isEqualTo(CONSUMER_LAST_NAME_1);
        assertThat(consumers.get(1).getConsumerId()).isEqualTo(CONSUMER_ID_2);
        assertThat(consumers.get(1).getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_2);
        assertThat(consumers.get(1).getLastName()).isEqualTo(CONSUMER_LAST_NAME_2);
    }
    @Test
    public void getAllEmptyConsumersTest(){
        when(consumerService.findAll())
                .then(invocation -> {
                    return null;
                });

        ResponseEntity<GetConsumersResponse> response = consumerController.findAll();
        //verifica che il servi<io findAll sia stato invocato
        verify(consumerService).findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
