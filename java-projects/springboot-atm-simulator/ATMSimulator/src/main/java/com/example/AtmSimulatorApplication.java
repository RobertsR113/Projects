package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtmSimulatorApplication
{

	public static void main(String[] args)
	{
		ClientStorage.readClients();
		LogStorage.getLogs();
		SpringApplication.run(AtmSimulatorApplication.class, args);
	}

}
