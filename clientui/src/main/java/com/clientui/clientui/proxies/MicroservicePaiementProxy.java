package com.clientui.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.clientui.clientui.beans.PaiementBean;

@FeignClient(name = "microservice-paiement", url = "localhost:9003")
public interface MicroservicePaiementProxy {

    @PostMapping(value = "/paiement")
    public ResponseEntity<PaiementBean> payerUneCommande(@RequestBody PaiementBean paiement);
}
