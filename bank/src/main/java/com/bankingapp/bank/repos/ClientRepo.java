package com.bankingapp.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingapp.bank.models.Client;

public interface ClientRepo extends JpaRepository<Client, Long>{

	
	

}
