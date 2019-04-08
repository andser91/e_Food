package it.uniroma3.sagas;

public class CreateOrderSaga {
/*
    private SagaDefinition<CreateOrderSagaState> sagaDefinition;

    public CreateOrderSaga(OrderServiceProxy orderService, ConsumerServiceProxy consumerService, KitchenServiceProxy kitchenService,
                           AccountingServiceProxy accountingService) {
        this.sagaDefinition =
                step()
                        .withCompensation(orderService.reject, CreateOrderSagaState::makeRejectOrderCommand)
                        .step()
                        .invokeParticipant(consumerService.validateOrder, CreateOrderSagaState::makeValidateOrderByConsumerCommand)
                        .step()
                        .invokeParticipant(kitchenService.create, CreateOrderSagaState::makeCreateTicketCommand)
                        .onReply(CreateTicketReply.class, CreateOrderSagaState::handleCreateTicketReply)
                        .withCompensation(kitchenService.cancel, CreateOrderSagaState::makeCancelCreateTicketCommand)
                        .step()
                        .invokeParticipant(accountingService.authorize, CreateOrderSagaState::makeAuthorizeCommand)
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
*/
}
