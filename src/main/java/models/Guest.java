package models;

import jdk.jfr.internal.Utils;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Guest{
        // Allows UI to delete guest by its temp id rather than send full name details which equals/hashCode use.
        @Transient
        private UUID tempId = UUID.randomUUID();

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Size(min = 2, max = 20)
        @NotNull(message = "required")
        @Column(nullable = false)
        private String firstName;

        @Size(min = 2, max = 20)
        @NotNull(message = "required")
        @Column(nullable = false)
        private String lastName;

        @column
        private String email;


        public Guest(String firstName, String lastName) {
            setFirstName(firstName);
            setLastName(lastName);
        }

        public Guest() {
        }

        public String getFirstName() {
            return firstName;
        }

        /**
         * Converts to lowercase for consistent equality checks
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName.toLowerCase();
        }

        public String getLastName() {
            return lastName;
        }

        /**
         * Converts to lowercase for consistent equality checks
         */
        public void setLastName(String lastName) {
            this.lastName = lastName.toLowerCase();
        }

        public String getFormattedFullName() {
            return Utils.capitalizeWords(firstName) + " " + Utils.capitalizeWords(lastName);
        }

        public UUID getTempId() {
            return tempId;


        /**
         * Cant use a UUID because that does not prevent a guest with the same name being added.
         */


    }

}
