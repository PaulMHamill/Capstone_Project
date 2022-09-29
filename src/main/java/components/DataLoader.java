package components;

import models.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import repositories.GuestRepository;
import repositories.PodRepository;
import repositories.ReservationRepository;

import java.text.ParseException;

@Profile("!test")
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    PodRepository podRepository;
    @Autowired
    ReservationRepository reservationRepository;

    public DataLoader() {
    }

    public void run(ApplicationArguments args) throws ParseException {

        Guest guest1 = new Guest("Paul", "Hamill", "PH@gmail.com");
        GuestRepository.save(Guest);



    }
}
