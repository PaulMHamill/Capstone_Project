package reservation.testcheckboxes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DrinkConverter implements Converter<String, Drink> {

    @Override
    public Drink convert(String id) {
        System.out.println("Trying to convert id=" + id + " into a drink");

        int parsedId = Integer.parseInt(id);
        List<Drink> selectableDrinks = Arrays.asList(
                new Drink(1L, "champagne"),
                new Drink(2L, "wine"),
                new Drink(3L, "beer")
        );
        int index = parsedId - 1;
        return selectableDrinks.get(index);
    }
}
