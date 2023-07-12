package com.clientui.clientui.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clientui.clientui.beans.CommandeBean;
import com.clientui.clientui.beans.PaiementBean;
import com.clientui.clientui.beans.ProductBean;
import com.clientui.clientui.exceptions.ProductBadRequestException;
import com.clientui.clientui.proxies.MicroserviceCommandesProxy;
import com.clientui.clientui.proxies.MicroservicePaiementProxy;
import com.clientui.clientui.proxies.MicroserviceProduitsProxy;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy produitsProxy;

    @Autowired
    private MicroserviceCommandesProxy commandesProxy;

    @Autowired
    private MicroservicePaiementProxy paiementProxy;

    // ******************* endpoint for products ********************
    @GetMapping(value = "/")
    public String accueil(Model model) {

        List<ProductBean> produits = produitsProxy.listDesProduits();
        model.addAttribute("produits", produits);
        return "Accueil";
    }

    @GetMapping(value = "/details-produit/{id}")
    public String ficheProduit(@PathVariable(value = "id") int id, Model model) {
        ProductBean produit = produitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);
        return "FicheProduit";
    }

    // ******************* endpoint for commandes ********************

    @GetMapping(value = "/commander-produit/{idProduit}/{prix}")
    public String commanderProduit(@PathVariable(value = "idProduit") int idProduit,
            @PathVariable(value = "prix") double prix,
            Model model) {

        ProductBean produit = produitsProxy.recupererUnProduit(idProduit);

        if (produit != null && produit.getPrix() == prix) {

            CommandeBean commande = new CommandeBean();
            commande.setProductId(idProduit);
            commande.setDateCommande(new Date());
            commande.setQuantite(1);
            commande.setCommandePayee(false);

            CommandeBean nouvelleCommande = commandesProxy.ajouterCommande(commande);

            model.addAttribute("commande", nouvelleCommande);
            model.addAttribute("produit", produit);
            model.addAttribute("prix", commande.getQuantite() * produit.getPrix());

            return "Paiement";

        } else {
            throw new ProductBadRequestException("Le produit n\' existe pas");
        }
    }

    // ******************* endpoint for paiement ********************

    @GetMapping(value = "/payer-commande/{id_commande}/{prixCommande}")
    public String payerCommande(@PathVariable(value = "id_commande") int idCommande,@PathVariable(value = "prixCommande")double prixCommande, Model model) {
        PaiementBean paiementAExecuter = new PaiementBean();
        paiementAExecuter.setIdCommande(idCommande);
        paiementAExecuter.setMontant(prixCommande);
        paiementAExecuter.setNumeroCarte(numeroCarte());
        

        Boolean paiementAccepte = false;

        ResponseEntity<PaiementBean> paiementResponse  = paiementProxy.payerUneCommande(paiementAExecuter);
        if (paiementResponse.getStatusCode() == HttpStatus.CREATED) {
            paiementAccepte = true;
        }

        model.addAttribute("paiementOk", paiementAccepte);

        return "Confirmation";

    }


    private Long numeroCarte() {
        return ThreadLocalRandom.current().nextLong(10000000L, 90000000L);
    }
}
