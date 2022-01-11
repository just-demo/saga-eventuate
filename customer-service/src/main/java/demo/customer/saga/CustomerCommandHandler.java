package demo.customer.saga;

import static demo.customer.api.channels.Channels.CUSTOMER_CHANNEL;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder.fromChannel;

import org.springframework.stereotype.Component;

import demo.customer.api.commands.ReserveCreditCommand;
import demo.customer.api.replies.CustomerCreditLimitExceeded;
import demo.customer.api.replies.CustomerCreditReserved;
import demo.customer.api.replies.CustomerNotFound;
import demo.customer.exception.CustomerCreditLimitExceededException;
import demo.customer.exception.CustomerNotFoundException;
import demo.customer.service.CustomerService;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomerCommandHandler {

  private final CustomerService customerService;

  public CommandHandlers commandHandlerDefinitions() {
    return fromChannel(CUSTOMER_CHANNEL)
        .onMessage(ReserveCreditCommand.class, this::reserveCredit)
        .build();
  }

  public Message reserveCredit(CommandMessage<ReserveCreditCommand> message) {
    ReserveCreditCommand command = message.getCommand();
    try {
      customerService.reserveCredit(command.getCustomerId(), command.getOrderId(), command.getOrderTotal());
      return withSuccess(new CustomerCreditReserved());
    } catch (CustomerNotFoundException e) {
      return withFailure(new CustomerNotFound());
    } catch (CustomerCreditLimitExceededException e) {
      return withFailure(new CustomerCreditLimitExceeded());
    }
  }
}
