package com.mpaiement.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mpaiement.beans.CommandeBean;
import com.mpaiement.dao.PaiementDao;
import com.mpaiement.model.Paiement;
import com.mpaiement.proxies.MicroserviceCommandesProxy;
import com.mpaiement.web.exceptions.PaiementExistantException;
import com.mpaiement.web.exceptions.PaiementImpossibleException;
import com.mpaiement.web.exceptions.config.GlobalErrorCode;


@RestController
public class PaiementController {

    @Autowired
    PaiementDao paiementDao;

    @Autowired
    private MicroserviceCommandesProxy commandeProxy;

    @PostMapping(value = "/paiement")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement){


        //Vérifions s'il y a déjà un paiement enregistré pour cette commande
        Paiement paiementExistant = paiementDao.findByidCommande(paiement.getIdCommande());
        if(paiementExistant != null) throw new PaiementExistantException("Cette commande est déjà payée",GlobalErrorCode.ERROR_PAIEMENT_EXISTANT);

        //Enregistrer le paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);


        if(nouveauPaiement == null) throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard",GlobalErrorCode.ERROR_PAIEMENT_IMPOSSIBLE);



        //Nous allons appeler le Microservice Commandes ici pour lui signifier que le paiement est accepté

        Optional<CommandeBean> optionalCommande = commandeProxy.recupererCommande(nouveauPaiement.getIdCommande());
       
        if (optionalCommande.isPresent()) {
            CommandeBean commande = optionalCommande.get();
            commande.setCommandePayee(true);
            commandeProxy.updateCommande(commande);
        } else {
            throw new PaiementImpossibleException("Erreur, impossible de retrouver la commande correspondante!",GlobalErrorCode.ERROR_PAIEMENT_IMPOSSIBLE);
        }


        return new ResponseEntity<>(nouveauPaiement, HttpStatus.CREATED);

    }




}
