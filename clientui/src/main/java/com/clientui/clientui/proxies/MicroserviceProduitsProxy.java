package com.clientui.clientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clientui.clientui.beans.ProductBean;

@FeignClient(name = "microservice-produits")
@RibbonClient(name= "microservice-produits")
public interface MicroserviceProduitsProxy {

    @GetMapping(value = "/produits")
    List<ProductBean> listDesProduits();

    @GetMapping(value = "/produits/{id}")
    ProductBean recupererUnProduit(@PathVariable(value = "id") int id);

}
