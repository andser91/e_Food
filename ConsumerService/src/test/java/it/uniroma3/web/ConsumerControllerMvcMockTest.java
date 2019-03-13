package it.uniroma3.web;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class ConsumerControllerMvcMockTest {

    @InjectMocks
    private ConsumerController consumerController;
    @Mock
    private ConsumerService consumerService;
   // @Autowired
    private MockMvc mockMvc;

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
        this.mockMvc = standaloneSetup(consumerController).build(); //quale è meglio autowired o questo?
        consumers = new ArrayList<Consumer>();
        Consumer consumer1 = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
        consumer1.setId(CONSUMER_ID_1);
        Consumer consumer2 = new Consumer(CONSUMER_FIRST_NAME_2, CONSUMER_LAST_NAME_2);
        consumer2.setId(CONSUMER_ID_2);
        consumers.add(consumer1);
        consumers.add(consumer2);
    }

    @Test
    public void postTest() throws Exception {
        when(consumerService.create(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                    consumer.setId(CONSUMER_ID_1);
                    return consumer;
                });

        String jsonRequest =
                "{ " +
                        "\"firstName\": \"" + CONSUMER_FIRST_NAME_1 + "\", " +
                        "\"lastName\":\"" + CONSUMER_LAST_NAME_1 + "\" " +
                        " }";
        mockMvc
                .perform(post("/consumers/").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consumerId").value(CONSUMER_ID_1));
    }

    @Test
    public void getTest() throws Exception {
        when(consumerService.findById(CONSUMER_ID_1))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                    consumer.setId(CONSUMER_ID_1);
                    return consumer;
                });

        mockMvc
                .perform(get("/consumers/{consumerId}", CONSUMER_ID_1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                /* verifica gli elementi della risposta JSON */
                .andExpect(jsonPath("$.consumerId").value(CONSUMER_ID_1))
                .andExpect(jsonPath("$.firstName").value(CONSUMER_FIRST_NAME_1))
                .andExpect(jsonPath("$.lastName").value(CONSUMER_LAST_NAME_1));
    }
    @Test
    public void getEmptyTest() throws Exception {
        when(consumerService.findById(CONSUMER_ID_1))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME_1, CONSUMER_LAST_NAME_1);
                    consumer.setId(CONSUMER_ID_1);
                    return consumer;
                });

        mockMvc
                .perform(get("/consumers/{consumerId}", CONSUMER_ID_NOT_EXISTING).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllTest() throws Exception {
        when(consumerService.findAll())
                .then(invocation -> {
                    return consumers;
                });

        mockMvc
                .perform(get("/consumers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consumers[0].consumerId").value(CONSUMER_ID_1))
                .andExpect(jsonPath("$.consumers[0].firstName").value(CONSUMER_FIRST_NAME_1))
                .andExpect(jsonPath("$.consumers[0].lastName").value(CONSUMER_LAST_NAME_1))
                .andExpect(jsonPath("$.consumers[1].consumerId").value(CONSUMER_ID_2))
                .andExpect(jsonPath("$.consumers[1].firstName").value(CONSUMER_FIRST_NAME_2))
                .andExpect(jsonPath("$.consumers[1].lastName").value(CONSUMER_LAST_NAME_2));
    }
}


