package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService (studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllStudents() {
        // When

        underTest.getAllStudents();

        //then

        verify(studentRepository).findAll();

    }

    @Test
    void addStudent() {
        // Given
        String email = "mssidaoui@gmail.com";
        Student student = new Student(
                "Sidaoui",
                email,
                Gender.MALE
        );
        underTest.addStudent(student);
        // Then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void willThrowWhenEmailIsTaken() {
        // Given
        String email = "mssidaoui@gmail.com";
        Student student = new Student(
                "Sidaoui",
                email,
                Gender.MALE
        );
        given(studentRepository.selectExistsEmail (student.getEmail()))
                .willReturn(true);        // Then
                assertThatThrownBy(() -> underTest.addStudent(student))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining ("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());
    }

    @Test
    @Disabled
    void deleteStudent() {
        //given
        String email = "mssidaoui@gmail.com";
        Student student = new Student(
                "Sidaoui",
                email,
                Gender.MALE
        );

        //when

        //then
    }
}