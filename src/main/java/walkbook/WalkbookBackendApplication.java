package walkbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WalkbookBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkbookBackendApplication.class, args);
    }

}
