package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	Calculator calculator=new Calculator();
	@Test
	void itShouldAddNumbers() {

		// Given
		int numberOne=25;
		int numberTwo=25;
		// When
		int result=calculator.add(numberOne,numberTwo);
		// Then
		int expected =50;
		assertThat(result).isEqualTo(expected);


	}


}

class Calculator {
	int add(int a,int b)
	{
		return a+b;
	}
}
