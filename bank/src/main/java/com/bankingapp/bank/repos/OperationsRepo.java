package com.bankingapp.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingapp.bank.models.Operations;

public interface OperationsRepo extends JpaRepository<Operations, Long> {

}
