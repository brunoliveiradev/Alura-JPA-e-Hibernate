package loja.DAO;

import loja.VO.RelatorioVendasVO;
import loja.model.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

    private final EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public void atualizar(Pedido pedido) {
        this.em.merge(pedido);
    }

    public void remover(Pedido pedido) {
        pedido = em.merge(pedido);
        this.em.remove(pedido);
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido AS p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    // Consulta de um relatório - sinxtase SELECT NEW utilizada quando precisa trazer varias colunas de tabelas variadas
    public List<RelatorioVendasVO> relatorioDeVendas() {
        String jpql = "SELECT new loja.VO.RelatorioVendasVO(produto.nome, SUM(item.quantidade), MAX(pedido.data)) " +
                "FROM Pedido AS pedido " +
                "JOIN pedido.itens AS item " +
                "JOIN item.produto AS produto " +
                "GROUP BY produto.nome " +
                "ORDER BY SUM(item.quantidade) DESC";
        return em.createQuery(jpql, RelatorioVendasVO.class).getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id){
        return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();

    }

}
