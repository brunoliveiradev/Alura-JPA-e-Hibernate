package loja.Services;

import loja.DAO.CategoriaDAO;
import loja.DAO.ProdutoDAO;
import loja.model.Categoria;
import loja.model.Produto;
import loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);

        Produto p = produtoDAO.buscarPorId(1L);
        System.out.println("Preço por ID: " + p.getPreco());

        List<Produto> buscarTodos = produtoDAO.buscarTodos();
        buscarTodos.forEach(produto -> System.out.println("Buscar todos: "+ p.getNome()));

        List<Produto> buscarNome = produtoDAO.buscarPorNomeProduto("Xiaomi Redmi");
        buscarNome.forEach(produto -> System.out.println("Busca por Nome do Produto: " + p.getNome()));

        List<Produto> buscarPorNomeCategoria = produtoDAO.buscarPorNomeCategoria("CELULAR");
        buscarPorNomeCategoria.forEach(produto -> System.out.println("Busca nome do produto por Nome da Categoria: " + p.getNome()));

        BigDecimal precoDoProduto = produtoDAO.buscarPorPrecoProdutoComNome("Xiaomi Redmi");
        System.out.println("Preço do Produto: " + precoDoProduto);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULAR");
        Produto celular = new Produto("Xiaomi Redmi", "Smartphone", new BigDecimal("800"), celulares);

        // Utiliza uma classe de suporte para pegar a conexão do Entity Manager
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);

        //Com os frameworks, as transações, o commit e o close são feito por eles

        // Iniciar uma transação
        em.getTransaction().begin();
        //Fazer insert na tabela
        categoriaDAO.cadastrar(celulares); // cadastro da categoria
        produtoDAO.cadastrar(celular);
        // Commitar a transação
        em.getTransaction().commit();
        // Fechar a transação
        em.close();
    }
}
