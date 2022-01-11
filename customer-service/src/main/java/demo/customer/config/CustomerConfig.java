package demo.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.customer.saga.CustomerCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;

// TODO: create handler for CustomerCreditLimitExceededException
@Configuration
public class CustomerConfig {

  @Bean
  public CommandDispatcher consumerCommandDispatcher(
      SagaCommandDispatcherFactory sagaCommandDispatcherFactory, CustomerCommandHandler customerCommandHandler) {
    return sagaCommandDispatcherFactory.make(
        "customerCommandDispatcher", customerCommandHandler.commandHandlerDefinitions());
  }
}
