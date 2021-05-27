package loja.util;

import loja.DAO.CategoriaDAO;
import loja.DAO.ClienteDAO;
import loja.DAO.ProdutoDAO;
import loja.model.Categoria;
import loja.model.Cliente;
import loja.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PopularBDUtil {

    public void popularBancoDeDados() {

        Categoria celulares = new Categoria("CELULAR");
        Categoria videogames = new Categoria("VIDEOGAME");
        Categoria informatica = new Categoria("INFORMATICA");

        Produto celular = new Produto("Xiaomi Redmi", "Smartphone", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("Playstation 5", "Console Última Geração", new BigDecimal("4000"), videogames);
        Produto computador = new Produto("PC Gamer", "Master Race", new BigDecimal("8000"), informatica);

        Cliente cliente = new Cliente("Bruno", "111.222.333-44");

        // Utiliza uma classe de suporte para pegar a conexão do Entity Manager
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        //Com os frameworks, as transações, o commit e o close são feito por eles

        // Iniciar uma transação
        em.getTransaction().begin();
        //Fazer insert na tabela de uma categoria, produto e cliente
        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(videogames);
        categoriaDAO.cadastrar(informatica);

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(videogame);
        produtoDAO.cadastrar(computador);

        clienteDAO.cadastrar(cliente);
        // Commitar a transação
        em.getTransaction().commit();
        // Fechar a transação
        em.close();
    }

}
