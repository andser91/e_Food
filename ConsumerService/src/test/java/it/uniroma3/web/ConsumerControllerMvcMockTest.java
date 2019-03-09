package it.uniroma3.web;

import it.uniroma3.CreateConsumerRequest;
import it.uniroma3.CreateConsumerResponse;
import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    private static Long CONSUMER_ID = 5L;
    private static Long CONSUMER_ID_NOT_EXISTING = 6L;
    private static String CONSUMER_FIRST_NAME = "John";
    private static String CONSUMER_LAST_NAME = "Smith";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(consumerController).build(); //quale è meglio autowired o questo?
    }

    @Test
    public void postTest() throws Exception {
        when(consumerService.create(CONSUMER_FIRST_NAME,CONSUMER_LAST_NAME))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME,CONSUMER_LAST_NAME);
                    consumer.setId(CONSUMER_ID);
                    return consumer;
                });

        String jsonRequest =
                "{ " +
                        "\"firstName\": \"" + CONSUMER_FIRST_NAME + "\", " +
                        "\"lastName\":\"" + CONSUMER_LAST_NAME + "\" " +
                        " }";
        mockMvc
                .perform(post("/consumers/").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consumerId").value(CONSUMER_ID));
    }

    @Test
    public void getTest() throws Exception {
        when(consumerService.findById(CONSUMER_ID))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
                    consumer.setId(CONSUMER_ID);
                    return consumer;
                });

        mockMvc
                .perform(get("/consumers/{consumerId}", CONSUMER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                /* verifica gli elementi della risposta JSON */
                .andExpect(jsonPath("$.consumerId").value(CONSUMER_ID))
                .andExpect(jsonPath("$.firstName").value(CONSUMER_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(CONSUMER_LAST_NAME));
    }
    @Test
    public void getEmptyTest() throws Exception {
        when(consumerService.findById(CONSUMER_ID))
                .then(invocation -> {
                    Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
                    consumer.setId(CONSUMER_ID);
                    return consumer;
                });

        mockMvc
                .perform(get("/consumers/{consumerId}", CONSUMER_ID_NOT_EXISTING).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


