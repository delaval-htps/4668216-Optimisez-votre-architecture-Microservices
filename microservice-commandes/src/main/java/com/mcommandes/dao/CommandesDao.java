package com.mcommandes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mcommandes.model.Commande;

public interface CommandesDao extends JpaRepository<Commande, Integer> {
}
