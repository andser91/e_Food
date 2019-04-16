package it.uniroma3.sagas;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import it.uniroma3.CreateTicketReply;
import it.uniroma3.domain.OrderService;
import it.uniroma3.sagaparticipants.ConsumerServiceProxy;
import it.uniroma3.sagaparticipants.KitchenServiceProxy;
import it.uniroma3.sagaparticipants.OrderServiceProxy;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaState> {

    private SagaDefinition<CreateOrderSagaState> sagaDefinition;

    public CreateOrderSaga(OrderServiceProxy orderService, ConsumerServiceProxy consumerService, KitchenServiceProxy kitchenService) {
        System.out.println("######### ENTRATO #########");
        this.sagaDefinition =
                step()
                        .withCompensation(orderService.reject, CreateOrderSagaState::makeRejectOrderCommand )
                        .step()
                        .invokeParticipant(consumerService.validateOrder , CreateOrderSagaState::makeValdateOrderByConsumerCommand)
                        .step()
                        .invokeParticipant(kitchenService.create, CreateOrderSagaState::makeCreateTicketCommand)
                        .onReply(CreateTicketReply.class, CreateOrderSagaState::handleCreateTicketReply)
                        .withCompensation(kitchenService.cancel, CreateOrderSagaState::makeCancelTicketCommand)
                        .step()
                        .invokeParticipant(kitchenService.confirmCreate, CreateOrderSagaState::makeConfirmCreateTicketCommand)
                        .step()
                        .invokeParticipant(orderService.approve, CreateOrderSagaState::makeApproveOrderCommand)
                        .build();

    }


    @Override
    public SagaDefinition<CreateOrderSagaState> getSagaDefinition() {
        return sagaDefinition;
    }

}
