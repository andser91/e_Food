package it.uniroma3.web;

import it.uniroma3.CreateConsumerRequest;
import it.uniroma3.CreateConsumerResponse;
import it.uniroma3.GetConsumerResponse;
import it.uniroma3.GetConsumersResponse;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class ConsumerControllerTest {
    @InjectMocks
    ConsumerController consumerController;

    @Mock
    ConsumerService consumerService;

    private static Long CONSUMER_ID = 5L;
    private static Long CONSUMER_ID_NOT_EXISTING = 6L;
    private static String CONSUMER_FIRST_NAME = "John";
    private static String CONSUMER_LAST_NAME = "Smith";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postTest(){
        //verifica operazione post

        //configura il ConsumerService.create
        when(consumerService.create(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME,CONSUMER_LAST_NAME);
                    consumer.setId(CONSUMER_ID);
                    return consumer;
                });
        //invoca operazione post
        CreateConsumerRequest request = new CreateConsumerRequest();
        request.setFirstName(CONSUMER_FIRST_NAME);
        request.setLastName(CONSUMER_LAST_NAME);
        CreateConsumerResponse response = consumerController.newConsumer(request);

        //verifica che il servizio sia stato invocato
        verify(consumerService).create(same(CONSUMER_FIRST_NAME),same(CONSUMER_LAST_NAME));

        assertThat(response.getConsumerId()).isEqualTo(CONSUMER_ID);

    }

    @Test
    public void getConsumerTest(){
        when(consumerService.findById(CONSUMER_ID))
                .then(invocation -> {
                   Consumer consumer = new Consumer(CONSUMER_FIRST_NAME,CONSUMER_LAST_NAME);
                   consumer.setId(CONSUMER_ID);
                   return consumer;
                });

        ResponseEntity<GetConsumerResponse> consumerResponseEntity = consumerController.findById(CONSUMER_ID);
         verify(consumerService).findById(same(CONSUMER_ID));

        assertThat(consumerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetConsumerResponse getConsumersResponse =consumerResponseEntity.getBody();
        assertThat(getConsumersResponse.getConsumerId()).isEqualTo(CONSUMER_ID);
        assertThat(getConsumersResponse.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME);
        assertThat(getConsumersResponse.getLastName()).isEqualTo(CONSUMER_LAST_NAME);
    }
    @Test
    public void getConsumerNotFoundTest(){
        when(consumerService.findById(CONSUMER_ID))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME,CONSUMER_LAST_NAME);
                    consumer.setId(CONSUMER_ID);
                    return Optional.of(consumer);
                });

        ResponseEntity<GetConsumerResponse> consumerResponseEntity = consumerController.findById(CONSUMER_ID_NOT_EXISTING);
        verify(consumerService).findById(CONSUMER_ID_NOT_EXISTING);

        assertThat(consumerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        GetConsumerResponse getConsumersResponse =consumerResponseEntity.getBody();
        assertThat(getConsumersResponse).isNull();
    }
}
