package helpdesk.config;

import helpdesk.services.DBService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@AllArgsConstructor
public class TestConfig {

    private DBService dbService;

    @Bean
    public boolean instanciaDB() {
        System.err.println("utilizar para criar o banco em H2");
        this.dbService.instanciaDB();
        return true;
    }
}
