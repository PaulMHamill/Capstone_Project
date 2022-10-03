package com.codeclan.example.bookingservice.reservationprocess;

import com.codeclan.example.bookingservice.models.Reservation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationForm {

    public enum Step {
        Dates(0),
        Guests(1),
        Review(2),
        Payment(3);

        int reservationProcess;

        Step(int reservationProcess) {
            this.reservationProcess = reservationProcess;
        }

        public static Step from(int reservationProcess) {
            switch (reservationProcess) {
                case 0:
                    return Dates;
                case 1:
                    return Guests;
                case 2:
                    return Review;
                case 3:
                    return Payment;
                default:
                    return Dates;
            }
        }
    }

    @Valid
    private Reservation reservation = new Reservation();

    private List<StepDescription> stepDescriptions = new ArrayList<>();

    private Set<Step> completedSteps = new HashSet<>();

    private Step activeStep = Step.Dates;

    public ReservationForm() {
        stepDescriptions.add(new StepDescription(0, "Dates", "Choose your reservation dates"));
        stepDescriptions.add(new StepDescription(1, "Guests", "Provide guest details"));
        stepDescriptions.add(new StepDescription(2, "Review", "Verify your reservation"));
        stepDescriptions.add(new StepDescription(3, "Payment", "Provide payment details"));
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setActive(Step step) {
        activeStep = step;
    }

    public Step getActiveStep() {
        return activeStep;
    }

    public StepDescription getActiveStepDescription() {
        return stepDescriptions.get(activeStep.reservationProcess);
    }

    public void completeStep(Step step) {
        completedSteps.add(step);
    }

    public void incompleteStep(Step step) {
        completedSteps.remove(step);
    }

    public boolean isActive(Step step) {
        return step == activeStep;
    }

    public boolean isCompleted(Step step) {
        return completedSteps.contains(step);
    }

    public void enterStep(Step step) {
        setActive(step);
        incompleteStep(step);
    }

    public List<StepDescription> getStepDescriptions() {
        return stepDescriptions;
    }

    public static class StepDescription {
        private int formStep;
        private String title;
        private String description;

        public StepDescription(int formStep, String title, String description) {
            this.formStep = formStep;
            this.title = title;
            this.description = description;
        }

        public int getFormStep() {
            return formStep;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        private int normalizedFormStep() {
            return formStep + 1;
        }

        public String getFormStepWithTitle() {
            return normalizedFormStep() + ". " + title;
        }

        public String getFormStepWithDescription() {
            return normalizedFormStep() + ". " + description;
        }

        @Override
        public String toString() {
            return "StepDescription{" +
                    "formStep=" + formStep +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

}
