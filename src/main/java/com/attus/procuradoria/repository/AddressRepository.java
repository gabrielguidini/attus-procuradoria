package com.attus.procuradoria.repository;

import com.attus.procuradoria.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
