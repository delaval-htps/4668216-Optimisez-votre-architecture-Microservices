package com.mpaiement.proxies;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mpaiement.beans.CommandeBean;

@FeignClient(name="microservice-commandes",url = "localhost:9002")
public interface MicroserviceCommandesProxy {
    
    @PutMapping(value = "/commandes")
    void updateCommande(@RequestBody CommandeBean commande);
    
    @GetMapping (value = "/commandes/{id}")
    Optional<CommandeBean> recupererCommande(@PathVariable(value = "id") int id);

}
