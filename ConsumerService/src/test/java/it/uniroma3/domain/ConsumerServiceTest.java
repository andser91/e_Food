package it.uniroma3.domain;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerRepository;
import it.uniroma3.domain.ConsumerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@SpringBootTest
public class ConsumerServiceTest {

    @InjectMocks
    private ConsumerService consumerService;
    @Mock
    private ConsumerRepository consumerRepository;

    private static final Long CONSUMER_ID_1 = 5L;
    private static final Long CONSUMER_ID_2 = 6L;
    private static final String CONSUMER_FIRST_NAME_1 = "John";
    private static final String CONSUMER_FIRST_NAME_2 = "Mary";
    private static final String CONSUMER_LAST_NAME_1 = "Smith";
    private static final String CONSUMER_LAST_NAME_2 = "Kate";

    private List<Consumer> consumersList;

    @Before
    public void setup(){
        /*inizializza i mock */
        MockitoAnnotations.initMocks(this);
        consumersList = new ArrayList<Consumer>();
        Consumer consumer1 = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
        consumer1.setId(CONSUMER_ID_1);
        Consumer consumer2 = new Consumer(CONSUMER_FIRST_NAME_2, CONSUMER_LAST_NAME_2);
        consumer2.setId(CONSUMER_ID_2);
        consumersList.add(consumer1);
        consumersList.add(consumer2);
    }

    @Test
    public void createConsumerTest() {
        /*verifica che venga creato un Consumer e che venga salvato
        tramite il repository nel db */
        when(consumerRepository.save(any(Consumer.class)))
                .then(invocation -> {
                    Consumer consumer = (Consumer) invocation.getArgument(0);
                    consumer.setId(CONSUMER_ID_1);
                    return consumer;
                });

        Consumer consumer = consumerService.create(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);

        verify(consumerRepository).save(same(consumer));
    }

    @Test
    public void findByIdTest() {
        /* verifica che venga trovato un Consumer a partire dal suo id e che se non lo trova restituisce un'eccezione*/
        when(consumerRepository.findById(CONSUMER_ID_1))
            .then(invocation -> {
                Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                consumer.setId(CONSUMER_ID_1);
                return Optional.of(consumer);
            });

        Consumer consumer = consumerService.findById(CONSUMER_ID_1);

        verify(consumerRepository).findById(same(CONSUMER_ID_1));
        assertThat(consumer.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_1);
        assertThat(consumer.getLastName()).isEqualTo(CONSUMER_LAST_NAME_1);
    }

    @Test
    public void findAllTest(){
        when(consumerRepository.findAll())
                .then(invocation -> {
                    return consumersList;
                });
        List<Consumer> consumers = consumerService.findAll();

        verify(consumerRepository).findAll();

        assertThat(consumers).isNotEmpty();
        assertThat(consumers.size()).isEqualTo(2);
        assertThat(consumers.get(0).getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_1);
        assertThat(consumers.get(0).getLastName()).isEqualTo(CONSUMER_LAST_NAME_1);
        assertThat(consumers.get(0).getId()).isEqualTo(CONSUMER_ID_1);
        assertThat(consumers.get(1).getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_2);
        assertThat(consumers.get(1).getLastName()).isEqualTo(CONSUMER_LAST_NAME_2);
        assertThat(consumers.get(1).getId()).isEqualTo(CONSUMER_ID_2);
    }

    @Test
    public void findAllEmptyTest(){
        when(consumerRepository.findAll())
                .then(invocation -> {
                    List<Consumer> consumers = new ArrayList<Consumer>();
                    return consumers;
                });
        List<Consumer> consumers = consumerService.findAll();

        verify(consumerRepository).findAll();

        assertThat(consumers.size()).isEqualTo(0);
        assertThat(consumers).isEmpty();
    }


}

