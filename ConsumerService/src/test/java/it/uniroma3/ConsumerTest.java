package it.uniroma3;

import it.uniroma3.domain.Consumer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConsumerTest {

    private Consumer consumer;

    @Before
    public void setup(){
         consumer = new Consumer("John","Smith");
    }

    @Test
    public void testGet(){
        assertThat(consumer.getFirstName()).isEqualTo("John");
        assertThat(consumer.getLastName()).isEqualTo("Smith");

    }
}
