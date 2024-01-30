package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @Test
    void shouldCreateDoctor() {
        String doctorName = "Ramón";
        String doctorLastName = "Përez";
        int doctorAge = 35;
        String doctorEmail = "ramonperez@miemail.es";

        Doctor myDoctor = new Doctor(doctorName, doctorLastName, doctorAge, doctorEmail);

        assertEquals(doctorName, myDoctor.getFirstName());
        assertEquals(doctorLastName, myDoctor.getLastName());
        assertEquals(doctorAge, myDoctor.getAge());
        assertEquals(doctorEmail, myDoctor.getEmail());
    }

    @Test
    void shouldCreateDoctorWithExpedtedId() {
        String doctorName = "Ramón";
        String doctorLastName = "Përez";
        int doctorAge = 35;
        String doctorEmail = "ramonperez@miemail.es";
        long doctorId = 3546546;

        Doctor myDoctor = new Doctor(doctorName, doctorLastName, doctorAge, doctorEmail);
        myDoctor.setId(doctorId);

        assertEquals(doctorId, myDoctor.getId());
    }

    @Test
    void shouldCreatePatient() {
        String patientName = "Germán";
        String patientLastName = "García";
        int patientAge = 26;
        String patientEmail = "germangarcia@miemail.es";

        Patient myPatient = new Patient(patientName, patientLastName, patientAge, patientEmail);

        assertEquals(patientName, myPatient.getFirstName());
        assertEquals(patientLastName, myPatient.getLastName());
        assertEquals(patientAge, myPatient.getAge());
        assertEquals(patientEmail, myPatient.getEmail());
    }

    @Test
    void shouldCreatePatientWithExpectedId() {
        String patientName = "Germán";
        String patientLastName = "García";
        int patientAge = 26;
        String patientEmail = "germangarcia@miemail.es";
        long patientId = 123455;

        Patient myPatient = new Patient(patientName, patientLastName, patientAge, patientEmail);

        myPatient.setId(patientId);

        assertEquals(patientId, myPatient.getId());
    }

    @Test
    void shouldCreateRoom() {
        String roomName = "Dermatology";

        Room myRoom = new Room(roomName);

        assertEquals(roomName, myRoom.getRoomName());
    }

    @Test
    void shouldCreateAppointment() {
        String name = "Germán";
        String lastName = "García";
        int age = 26;
        String email = "germangarcia@miemail.es";

        Patient myPatient = new Patient(name, lastName, age, email);

        String doctorName = "Ramón";
        String doctorLastName = "Përez";
        int doctorAge = 35;
        String doctorEmail = "ramonperez@miemail.es";

        Doctor myDoctor = new Doctor(doctorName, doctorLastName, doctorAge, doctorEmail);

        String roomName = "Dermatology";

        Room myRoom = new Room(roomName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt = LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment myAppointment = new Appointment(myPatient, myDoctor, myRoom, startsAt, finishesAt);

        assertEquals(myPatient, myAppointment.getPatient());
        assertEquals(myDoctor, myAppointment.getDoctor());
        assertEquals(myRoom, myAppointment.getRoom());
        assertEquals(startsAt, myAppointment.getStartsAt());
        assertEquals(finishesAt, myAppointment.getFinishesAt());
    }
    
    @Test
    void shouldCreateAppointmentWithExpectedId() {
        String name = "Germán";
        String lastName = "García";
        int age = 26;
        String email = "germangarcia@miemail.es";

        Patient myPatient = new Patient(name, lastName, age, email);

        String doctorName = "Ramón";
        String doctorLastName = "Përez";
        int doctorAge = 35;
        String doctorEmail = "ramonperez@miemail.es";

        Doctor myDoctor = new Doctor(doctorName, doctorLastName, doctorAge, doctorEmail);

        String roomName = "Dermatology";

        Room myRoom = new Room(roomName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt = LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment myAppointment = new Appointment(myPatient, myDoctor, myRoom, startsAt, finishesAt);
        long appointmentId = 123456;
        
        myAppointment.setId(appointmentId);
        
        assertEquals(appointmentId, myAppointment.getId());
    }
}
