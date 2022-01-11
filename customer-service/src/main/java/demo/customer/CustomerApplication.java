package demo.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;

@SpringBootApplication
@Import(OptimisticLockingDecoratorConfiguration.class)
@EnableJpaRepositories
public class CustomerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }
}
