package com.attus.procuradoria.repository;

import com.attus.procuradoria.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
