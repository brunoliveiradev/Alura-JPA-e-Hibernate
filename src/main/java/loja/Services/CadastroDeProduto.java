package loja.Services;

import loja.DAO.ProdutoDAO;
import loja.model.Produto;
import loja.util.PopularBDUtil;
import loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        PopularBDUtil novo = new PopularBDUtil();
        novo.popularBancoDeDados();

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

}
