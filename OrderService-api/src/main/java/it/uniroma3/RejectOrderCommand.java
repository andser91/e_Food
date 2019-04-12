package it.uniroma3;

public class RejectOrderCommand extends OrderCommand {
    private RejectOrderCommand() {
    }

    public RejectOrderCommand(long orderId) {
        super(orderId);
    }
}
