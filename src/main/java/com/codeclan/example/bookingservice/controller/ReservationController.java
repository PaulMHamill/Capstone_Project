package com.codeclan.example.bookingservice.controller;

import com.codeclan.example.bookingservice.models.Guest;
import com.codeclan.example.bookingservice.models.NotFoundException;
import com.codeclan.example.bookingservice.models.Pod;
import com.codeclan.example.bookingservice.models.Reservation;
import com.codeclan.example.bookingservice.repositories.PodRepository;
import com.codeclan.example.bookingservice.repositories.ReservationRepository;
import com.codeclan.example.bookingservice.reservationprocess.ReservationDates;
import com.codeclan.example.bookingservice.reservationprocess.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PodRepository podRepository;

//    @GetMapping("/reservations")
//    public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(name = "date", required = false) Date date){
//
//        if (date != null){
//            return new ResponseEntity<>(reservationRepository.findAllByDate(date), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
//    }

    @GetMapping("/reservations")
    public ResponseEntity <List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value="/reservations/{id}")
    public ResponseEntity<Reservation> putReservation(@RequestBody Reservation reservation, @PathVariable Long id) {
        Reservation reservationToUpdate = reservationRepository.findById(id).get();
        reservationToUpdate.setDateTo(reservation.getDateTo());
        reservationToUpdate.setDateFrom(reservation.getDateFrom());
        reservationToUpdate.setPod(reservation.getPod());
        reservationToUpdate.setGuest(reservation.getGuest());
        reservationToUpdate.setNumberOfNights(reservation.getNumberOfNights());
        reservationRepository.save(reservationToUpdate);
        return new ResponseEntity<>(reservationToUpdate, HttpStatus.OK);
    }

    @DeleteMapping(value="/reservations/{id}")
    public ResponseEntity<Long> deleteReservation(@PathVariable Long id){
        reservationRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ModelAttribute("reservationProcess")
    public ReservationForm getReservationForm() {
        return new ReservationForm();
    }

    // Form step 0

    /**
     * Entry point to begin the reservation flow.
     */
    @GetMapping("/reservation")
    public String getDateForm(@RequestParam(value = "podId") Long podId,
                              @ModelAttribute("reservationForm") ReservationForm reservationForm)
            throws NotFoundException {
        reservationForm.enterStep(ReservationForm.Step.Dates);

        Optional<Pod> maybePod = podRepository.findById(podId);
        if (!maybePod.isPresent()) {
            throw new NotFoundException();
        }

        maybePod.get().setReservation(reservationForm.getReservation());

        return "reservation/dates";
    }

    @PostMapping("/reservation/dates")
    public String dates(@Valid @ModelAttribute("reservationForm") ReservationForm reservationForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        reservationForm.enterStep(ReservationForm.Step.Dates);

        if (bindingResult.hasErrors()) {
            return "reservation/dates";
        }

        Optional<ReservationDates.ValidationError> validationError =
                reservationForm.getReservation().getDates().validate(timeProvider.localDate());

        if (validationError.isPresent()) {
            // Must be a field rejection as the dates form is bound to the property path reservation.dates.
            bindingResult.rejectValue("reservation.dates", validationError.get().getCode(),
                    validationError.get().getReason());
            return "reservation/dates";
        }

        reservationForm.completeStep(ReservationForm.Step.Dates);
        redirectAttributes.addFlashAttribute("reservationForm", reservationForm);
        return "redirect:/reservation/guests";
    }

    @PostMapping(value = "/reservation/dates", params = "cancel")
    public String cancelDates(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    /**
     * No need to validate since errors are implied through a negative total night duration which simplifies the logic.
     */
    @PostMapping(value = "/reservation/dates", params = "prices")
    public String roomCostFragment(@ModelAttribute("reservationForm") ReservationForm reservationForm) {
        return "reservation/fragments :: podCosts";
    }

    // Form step 1

    @GetMapping("/reservation/guests")
    public String getGuestForm(@ModelAttribute("reservationForm") ReservationForm reservationForm, Model model) {
        reservationForm.enterStep(ReservationForm.Step.Guests);
        model.addAttribute("guest", new Guest());
        return "reservation/guests";
    }

    @PostMapping(value = "/reservation/guests", params = "back")
    public String fromGuestBackToDates(@ModelAttribute("reservationForm") ReservationForm reservationForm,
                                       RedirectAttributes ra) {
        reservationForm.enterStep(ReservationForm.Step.Guests);
        ra.addFlashAttribute("reservationForm", reservationForm);
        return "redirect:/reservation?podId=" + reservationForm.getReservation().getPod().getId();
    }

    @PostMapping(value = "/reservation/guests", params = "addGuest")
    public String postAddGuest(@Valid @ModelAttribute("guest") Guest guest,
                               BindingResult bindingResult,
                               @ModelAttribute("reservationForm") ReservationForm reservationForm,
                               Model model) {
        reservationForm.enterStep(ReservationForm.Step.Guests);

        if (bindingResult.hasErrors()) {
            return "reservation/guests";
        }

        if (reservationForm.getReservation().getGuests().contains(guest)) {
            bindingResult.reject("exists", "A guest with this name already exists");
            return "reservation/guests";
        }

        if (reservationForm.getReservation().isPodFull()) {
            bindingResult.reject("guestLimitExceeded", "This pod has the maximum number of guests");
            return "reservation/guests";
        }

        reservationForm.getReservation().addGuest(guest);

        // create a new guest to rebind to the guest form
        model.addAttribute("guest", new Guest());

        return "reservation/guests";
    }

    @PostMapping(value = "/reservation/guests", params = "removeGuest")
    public String postRemoveGuest(@RequestParam("removeGuest") UUID guestId,
                                  @ModelAttribute("reservationForm") ReservationForm reservationForm,
                                  Model model) {
        reservationForm.enterStep(ReservationForm.Step.Guests);
        reservationForm.getReservation().removeGuestById(guestId);
        model.addAttribute("guest", new Guest());
        return "reservation/guests";
    }

    @PostMapping(value = "/reservation/guests")
    public String postGuestToExtras(@ModelAttribute(binding = false) Guest guest,
                                    Errors errors,
                                    @ModelAttribute("reservationForm") ReservationForm reservationForm,
                                    RedirectAttributes redirectAttributes) {
        reservationForm.enterStep(ReservationForm.Step.Guests);

        if (!reservationForm.getReservation().hasGuests()) {
            errors.reject("guests.noneExist", "There must be at least 1 guest");
            return "reservation/guests";
        }
        if (!reservationForm.getReservation().hasAtLeastOneAdultGuest()) {
            errors.reject("guests.noAdults", "There must be at least 1 adult");
            return "reservation/guests";
        }

        redirectAttributes.addFlashAttribute("reservationForm", reservationForm);

        reservationForm.completeStep(ReservationForm.Step.Guests);
        return "redirect:/reservation/extras";
    }


    // Form step 2 - review


    @GetMapping("/reservation/review")
    public String getReview(@ModelAttribute("reservationFlow") ReservationForm reservationForm) {
        reservationForm.setActive(ReservationForm.Step.Review);
        return "reservation/review";
    }

    @PostMapping(value = "/reservation/review", params = "back")
    public String fromReviewBackToGuests(@ModelAttribute("reservationFlow") ReservationForm reservationForm,
                                            RedirectAttributes ra) {
        reservationForm.setActive(ReservationForm.Step.Review);
        ra.addFlashAttribute("reservationForm", reservationForm);
        return "redirect:/reservation/guests";
    }

    @PostMapping("/reservation/review")
    public String postReview(@ModelAttribute("reservationForm") ReservationForm reservationForm,
                             RedirectAttributes ra) {
        reservationForm.setActive(ReservationForm.Step.Review);

        ra.addFlashAttribute("reservationFlow", reservationForm);
        reservationForm.completeStep(ReservationForm.Step.Review);
        return "redirect:/reservation/payment";
    }


    // Form step 3 - payment

    @GetMapping("/reservation/payment")
    public String getPayment(@ModelAttribute("reservationForm") ReservationForm reservationForm,
                             Model model) {
        reservationForm.setActive(ReservationForm.Step.Payment);
        model.addAttribute("pendingPayment", new PendingPayment(LocalDateTime.now()));
        return "reservation/payment";
    }

    @PostMapping(value = "/reservation/payment", params = "back")
    public String fromPaymentBackToReview(@ModelAttribute("reservationForm") ReservationForm reservationForm,
                                          RedirectAttributes ra) {
        reservationForm.setActive(ReservationForm.Step.Payment);
        ra.addFlashAttribute("reservationForm", reservationForm);
        return "redirect:/reservation/review";
    }

    @PostMapping(value = "/reservation/payment", params = "cancel")
    public String cancelPayment(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PostMapping("/reservation/payment")
    public String postPayment(@ModelAttribute("reservationForm") ReservationForm reservationForm,
                              @Valid @ModelAttribute("pendingPayment") PendingPayment pendingPayment,
                              BindingResult bindingResult, SessionStatus sessionStatus) {
        reservationForm.setActive(ReservationForm.Step.Payment);

        if (bindingResult.hasErrors()) {
            return "reservation/payment";
        }

        Reservation reservation = reservationForm.getReservation();
        // Simulate making a valid payment
        reservation.setCompletedPayment(pendingPayment.toCompletedPayment());

        /*
         * The new reservation is saved through the Pod since the pod owns the reservation in the
         * bi directional 1 to 1 mapping. This is to allow easier querying to identity pods that
         * have reservations.
         */
        podRepository.save(reservation.getPod());
        sessionStatus.setComplete();

        reservationForm.completeStep(ReservationForm.Step.Payment);
        return "redirect:/reservation/completed";
    }

    // End form

    @GetMapping("/reservation/completed")
    public String getFormCompleted() {
        return "reservation/completed";
    }




}
