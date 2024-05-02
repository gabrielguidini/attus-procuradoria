package com.attus.procuradoria.repository;

import com.attus.procuradoria.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = """
            select * from client c
            	inner join client_client_address cca on cca.client_id = c.id
            	inner join address a on cca.client_address_address_id  = a.address_id
            where c.client_uuid = :uuid""", nativeQuery = true)
    Optional<Client> findByClientUuid(@Param("uuid") UUID uuid);
}
