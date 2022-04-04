package writeside.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import writeside.application.BookingService;
import writeside.application.command.BookRoomsCommand;
import writeside.application.command.CancelRoomCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class WriteRestController {

    // http://localhost:8081/swagger-ui/index.html

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/createBooking")
    public boolean createBooking(
            @RequestParam String scnr,
            @RequestParam String[] roomsArray,
            @RequestParam String arrivalDateString,
            @RequestParam String departureDateString
    ) {
        LocalDate arrivalDate = LocalDate.parse(arrivalDateString, DateTimeFormatter.ISO_DATE);
        LocalDate departureDate = LocalDate.parse(departureDateString, DateTimeFormatter.ISO_DATE);
        List<String> rooms = Arrays.stream(roomsArray).collect(Collectors.toList());

        BookRoomsCommand bookRooms = new BookRoomsCommand(
                scnr,
                rooms,
                arrivalDate,
                departureDate
        );

        bookingService.book(bookRooms);
        return true;
    }

    /**
     * Example uuid for test with swagger ui f2700e61-802c-419a-89ba-ed8b93ed00f6
     */
    @PostMapping(value = "/cancelBooking")
    public boolean cancelBooking(@RequestParam String bookingIdString) {
        CancelRoomCommand cancelRoom = new CancelRoomCommand(UUID.fromString(bookingIdString));
        bookingService.cancel(cancelRoom);
        return true;
    }

}
