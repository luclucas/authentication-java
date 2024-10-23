package com.lulu.loja.controllers;

import com.lulu.loja.models.Produto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/produto")
public class ProdutoController {


    @GetMapping()
    public List<Produto> getProdutos(){
        Produto produto =new Produto(1L, "Smartphone", "Smartphone top de linha", new BigDecimal(1000.0), 10);
        Produto produto2 = new Produto(1L, "Smartphone", "Smartphone top de linha", new BigDecimal(1000.0), 10);
        ArrayList<Produto> list = new ArrayList<>();
        list.add(produto);
        list.add(produto2);

        return list;
    }
}
