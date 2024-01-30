package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetDoctor() throws Exception {
        Doctor doctor = new Doctor("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoDoctor() throws Exception {
        List<Doctor> doctors = new ArrayList<>();

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        Doctor doctor = new Doctor("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        long doctorId = 12345;
        doctor.setId(doctorId);

        Optional<Doctor> responseDoctor = Optional.of((Doctor) doctor);

        assertThat(responseDoctor).isPresent();
        assertThat(responseDoctor.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(responseDoctor);
        String url = String.format("/api/doctors/%s", doctorId);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception {
        long notCreatedDoctorId = 78945;

        Optional<Doctor> responseDoctor = Optional.empty();

        when(doctorRepository.findById(notCreatedDoctorId)).thenReturn(responseDoctor);

        String url = String.format("/api/doctors/%s", notCreatedDoctorId);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        Doctor doctor = new Doctor("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        long doctorId = 12345;
        doctor.setId(doctorId);

        Optional<Doctor> responseDoctor = Optional.of((Doctor) doctor);

        assertThat(responseDoctor).isPresent();
        assertThat(responseDoctor.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(responseDoctor);
        doctorRepository.deleteById(doctorId);
        String url = String.format("/api/doctors/%s", doctorId);
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctorById() throws Exception {
        long doctorId = 12345;

        Optional<Doctor> responseDoctor = Optional.empty();

        when(doctorRepository.findById(doctorId)).thenReturn(responseDoctor);
        doctorRepository.deleteById(doctorId);
        String url = String.format("/api/doctors/%s", doctorId);
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception {

        doctorRepository.deleteAll();

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Perl", "Jam", 24, "p.jam@hospital.accwe");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetPatient() throws Exception {
        Patient patient = new Patient("Perl", "Jam", 24, "p.jam@hospital.accwe");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatient() throws Exception {
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetPatientById() throws Exception {
        Patient patient = new Patient("Perl", "Jam", 24, "p.jam@hospital.accwe");
        long patientId = 75896;
        patient.setId(patientId);

        Optional<Patient> patientResponse = Optional.of((Patient) patient);

        assertThat(patientResponse).isPresent();
        assertThat(patientResponse.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(patientId);

        when(patientRepository.findById(patientId)).thenReturn(patientResponse);
        String url = String.format("/api/patients/%s", patientId);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception {
        long notCreatedpatientId = 75896;

        Optional<Patient> patientResponse = Optional.empty();

        when(patientRepository.findById(notCreatedpatientId)).thenReturn(patientResponse);

        String url = String.format("/api/patients/%s", notCreatedpatientId);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        Patient patient = new Patient("Perl", "Jam", 24, "p.jam@hospital.accwe");
        long patientId = 75896;
        patient.setId(patientId);

        Optional<Patient> patientResponse = Optional.of((Patient) patient);

        assertThat(patientResponse).isPresent();
        assertThat(patientResponse.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(patientId);

        when(patientRepository.findById(patientId)).thenReturn(patientResponse);
        String url = String.format("/api/patients/%s", patientId);
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatient() throws Exception {
        long patientId = 75896;

        Optional<Patient> patientResponse = Optional.empty();

        when(patientRepository.findById(patientId)).thenReturn(patientResponse);
        String url = String.format("/api/patients/%s", patientId);
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception {

        patientRepository.deleteAll();

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("Dermatology");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetRoom() throws Exception {
        Room room = new Room("Dermatology");

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyRoom() throws Exception {

        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception {
        Room room = new Room("Dermatology");
        String roomName = "Dermatology";

        Optional<Room> roomResponse = Optional.of((Room) room);

        assertThat(roomResponse).isPresent();
        assertThat(roomResponse.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo(roomName);

        when(roomRepository.findByRoomName(roomName)).thenReturn(roomResponse);

        String url = String.format("/api/rooms/%s", roomName);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyRoomByRoomName() throws Exception {

        String roomName = "Dermatology";

        Optional<Room> roomResponse = Optional.empty();

        when(roomRepository.findByRoomName(roomName)).thenReturn(roomResponse);

        String url = String.format("/api/rooms/%s", roomName);

        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteRoomByRoomName() throws Exception {
        Room room = new Room("Dermatology");
        String roomName = "Dermatology";

        Optional<Room> roomResponse = Optional.of((Room) room);

        assertThat(roomResponse).isPresent();
        assertThat(roomResponse.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo(roomName);

        when(roomRepository.findByRoomName(roomName)).thenReturn(roomResponse);

        String url = String.format("/api/rooms/%s", roomName);

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoomByRoomName() throws Exception {

        String roomName = "Dermatology";

        Optional<Room> roomResponse = Optional.empty();

        when(roomRepository.findByRoomName(roomName)).thenReturn(roomResponse);

        String url = String.format("/api/rooms/%s", roomName);

        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldDeleteAllRooms() throws Exception {

        roomRepository.deleteAll();

        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }
}
