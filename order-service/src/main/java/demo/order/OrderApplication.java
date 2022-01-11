package demo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;

@SpringBootApplication
@EnableJpaRepositories
@Import(OptimisticLockingDecoratorConfiguration.class)
public class OrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }

}
