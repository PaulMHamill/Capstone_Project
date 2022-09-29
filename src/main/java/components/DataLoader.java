package components;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Profile("!test")
@Component
public class DataLoader implements ApplicationRunner {

    public DataLoader() {
    }

    public void run(ApplicationArguments args) throws ParseException {

    }
}
