package com.mpaiement.dao;

import com.mpaiement.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementDao extends JpaRepository<Paiement, Integer>{

    Paiement findByidCommande(int idCommande);
}
