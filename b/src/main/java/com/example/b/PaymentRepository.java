package com.example.b;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends CrudRepository<Payment, UUID>, PagingAndSortingRepository<Payment, UUID> {

	Page<Payment> findByCurrency(String currency, Pageable pageable);
}