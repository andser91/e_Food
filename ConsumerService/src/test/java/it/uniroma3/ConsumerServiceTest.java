package it.uniroma3;

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

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.mockito.Mockito.*;
@SpringBootTest
public class ConsumerServiceTest {

    @InjectMocks
    private ConsumerService consumerService;
    @Mock
    private ConsumerRepository consumerRepository;

    private static final Long CONSUMER_ID = 5L;
    private static final String CONSUMER_FIRST_NAME = "John";
    private static final String CONSUMER_LAST_NAME = "Smith";



    @Before
    public void setup(){
        /*inizializza i mock */
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createConsumerTest() {
        /*verifica che venga creato un Consumer e che venga salvato
        tramite il repository nel db */
        when(consumerRepository.save(any(Consumer.class)))
                .then(invocation -> {
                    Consumer consumer = (Consumer) invocation.getArgument(0);
                    consumer.setId(CONSUMER_ID);
                    return consumer;
                });

        Consumer consumer = consumerService.create(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);

        verify(consumerRepository).save(same(consumer));
    }

    @Test
    public void findByIdTest() {
        /* verifica che venga trovato un Consumer a partire dal suo id e che se non lo trova restituisce un'eccezione*/
        when(consumerRepository.findById(CONSUMER_ID))
            .then(invocation -> {
                Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
                consumer.setId(CONSUMER_ID);
                return Optional.of(consumer);
            });

        Consumer consumer = consumerService.findById(CONSUMER_ID);

        verify(consumerRepository).findById(same(CONSUMER_ID));
        assertThat(consumer.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME);
        assertThat(consumer.getLastName()).isEqualTo(CONSUMER_LAST_NAME);
    }



}

