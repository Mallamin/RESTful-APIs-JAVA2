package com.bankingapp.bank.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingapp.bank.models.Funds;

public interface FundsRepo extends JpaRepository<Funds, Long>{

}
