package loja.DAO;

import loja.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDAO {

    private final EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto) {
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

    // Create NamedQuery - onde a query é passada na entidade e não na classeDAO
    public List<Produto> buscarPorNomeCategoria(String nomeCategoria) {
        return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
                .setParameter("nome", nomeCategoria)
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

    // Consultas com parâmetros OPCIONAIS dinâmicos
    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        // Fazendo verificação, para ver se um dos filtros está preenchido
        String jpql = "SELECT p FROM Produto p WHERE 1=1 "; //GAMBIARRA POIS PRECISA DO WHERE
        boolean verificaNomeNullOuVazio = nome != null & !nome.trim().isEmpty();
        //Verifica primeiro se os parametros são diferente de null ou vazio
        if (verificaNomeNullOuVazio) {
            jpql += " AND p.nome = :nome ";
        }
        if (preco != null) {
            jpql += " AND p.preco = :preco ";
        }
        if (dataCadastro != null) {
            jpql += " AND p.dataCadastro = :dataCadastro ";
        }

        // Setando os parametros preenchidos
        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
        if (verificaNomeNullOuVazio) {
            query.setParameter("nome", nome);
        }
        if (preco != null) {
            query.setParameter("preco", preco);
        }
        if (dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    // Consulta com Criteria API - Usando a JPA
    public List<Produto> buscarPorParametrosCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class); // O Select é da mesma entidade, basta colocar o FROM
        //Para fazer os filtros
        Predicate filtros = builder.and();

        //Setando os parametros
        if (nome != null & !nome.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if (preco != null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if (dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }
        query.where(filtros);

        return em.createQuery(query).getResultList();
    }



}