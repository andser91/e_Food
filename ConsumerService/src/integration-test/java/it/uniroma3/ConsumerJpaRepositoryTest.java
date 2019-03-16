package it.uniroma3;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerJpaRepositoryTest {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final String CONSUMER_FIRST_NAME_1 = "John";
    private static final String CONSUMER_LAST_NAME_1 = "Smith";
    private static final String CONSUMER_FIRST_NAME_2 = "Mary";
    private static final String CONSUMER_LAST_NAME_2 = "Kate";


    @Test
    public void saveAndFindByIdConsumerTest(){
        Long consumerId = transactionTemplate.execute((ts) -> {
           Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
           consumerRepository.save(consumer);
           return consumer.getId();
        });

        transactionTemplate.execute((ts)->
        {
            Consumer consumer = consumerRepository.findById(consumerId).get();

            assertThat(consumer.getLastName()).isEqualTo(CONSUMER_LAST_NAME_1);
            assertThat(consumer.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME_1);
            return null;
        });
    }

    @Test
    public void saveAndDeleteByIdConsumerTest() {
        Long consumerId = transactionTemplate.execute((ts) -> {
            Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
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

    @Test
    public void findAllConsumerTest(){
        List<Consumer> consumers = transactionTemplate.execute((ts) -> {
            Consumer c1 = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
            Consumer c2 = new Consumer(CONSUMER_FIRST_NAME_2, CONSUMER_LAST_NAME_2);
            List<Consumer> consumerList = new ArrayList<Consumer>();
            consumerList.add(c1);
            consumerList.add(c2);
            consumerRepository.save(c1);
            consumerRepository.save(c2);
            return consumerList;
        } );

        transactionTemplate.execute((ts) -> {
            List<Consumer> consumerList = (List<Consumer>) consumerRepository.findAll();
            assertThat(consumerList.size()).isEqualTo(consumers.size());
            assertThat(consumerList.get(0).getFirstName()).isEqualTo(consumers.get(0).getFirstName());
            assertThat(consumerList.get(1).getFirstName()).isEqualTo(consumers.get(1).getFirstName());
            assertThat(consumerList.get(0).getLastName()).isEqualTo(consumers.get(0).getLastName());
            assertThat(consumerList.get(1).getLastName()).isEqualTo(consumers.get(1).getLastName());
            return null;
        });
    }

    @Test
    public void findAllEmptyConsumerTest() {
        transactionTemplate.execute((ts) -> {
                    consumerRepository.deleteAll();
                    return null;
                });
        transactionTemplate.execute((ts) -> {
            List<Consumer> consumerList = (List<Consumer>) consumerRepository.findAll();
            assertThat(consumerList.size()).isEqualTo(0);
            assertThat(consumerList).isEmpty();
            return null;
        });
    }
}
