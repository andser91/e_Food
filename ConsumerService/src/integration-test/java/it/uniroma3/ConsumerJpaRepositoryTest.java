package it.uniroma3;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerJpaRepositoryTest {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final String CONSUMER_FIRST_NAME = "John";
    private static final String CONSUMER_LAST_NAME = "Smith";
    private static final Long CONSUMER_ID = 5L;

    @Test
    public void saveAndFindByIdConsumerTest(){
        Long consumerId = transactionTemplate.execute((ts) -> {
           Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
           consumerRepository.save(consumer);
           return consumer.getId();
        });

        transactionTemplate.execute((ts)->
        {
            Consumer consumer = consumerRepository.findById(consumerId).get();

            assertThat(consumer.getLastName()).isEqualTo(CONSUMER_LAST_NAME);
            assertThat(consumer.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME);
            return null;
        });
    }

    @Test
    public void saveAndDeleteByIdConsumerTest() {
        Long consumerId = transactionTemplate.execute((ts) -> {
            Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
            consumerRepository.save(consumer);
            return consumer.getId();
        });

        transactionTemplate.execute((ts) -> {
            Consumer consumerExisting = consumerRepository.findById(consumerId).get();

            assertThat(consumerExisting).isNotNull();
            consumerRepository.deleteById(consumerId);
            Optional<Consumer> consumerNull = consumerRepository.findById(consumerId);
            assertThat(consumerNull).isEmpty();
            return null;
        } );
    }
}
