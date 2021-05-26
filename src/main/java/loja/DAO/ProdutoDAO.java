package loja.DAO;

import loja.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {

    private final EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto){
        this.em.merge(produto);
    }

    public void remover(Produto produto) {
        produto = em.merge(produto);
        this.em.remove(produto);
    }

    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        // Recebe o nome da entidade ao invés da tabela
        String jpql = "SELECT p FROM Produto AS p";
        //CreateQuery cria o comando apenas, mas retornar os resultados é o getResultList
        return em.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNomeProduto(String nomeProduto) {
        // named parameter :nome, parameter ordinal = ?1 ?2 ...
        String jpql = "SELECT p FROM Produto AS p WHERE p.nome = :nome";
        return em.createQuery(jpql, Produto.class)
                .setParameter("nome", nomeProduto)
                .getResultList();
    }

    public List<Produto> buscarPorNomeCategoria(String nomeCategoria) {
        String jpql = "SELECT p FROM Produto AS p WHERE p.categoria.nome = ?1";
        return em.createQuery(jpql, Produto.class)
                .setParameter(1, nomeCategoria)
                .getResultList();
    }

    // Limitando dados de uma consulta
    public BigDecimal buscarPorPrecoProdutoComNome(String nomeProduto) {
        // named parameter :nome, parameter ordinal = ?1 ?2 ...
        String jpql = "SELECT p.preco FROM Produto AS p WHERE p.nome = :nome";
        return em.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nomeProduto)
                .getSingleResult();
    }
}
