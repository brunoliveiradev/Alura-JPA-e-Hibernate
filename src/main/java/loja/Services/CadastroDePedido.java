package loja.Services;

import loja.DAO.ClienteDAO;
import loja.DAO.PedidoDAO;
import loja.DAO.ProdutoDAO;
import loja.VO.RelatorioVendasVO;
import loja.model.Cliente;
import loja.model.ItemPedido;
import loja.model.Pedido;
import loja.model.Produto;
import loja.util.JPAUtil;
import loja.util.PopularBDUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        // Criar e salvar um produto em banco de dados H2;
        PopularBDUtil novo = new PopularBDUtil();
        novo.popularBancoDeDados();
        // Precisa carregar/recuperar o produto para passar ele para um pedido
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);
        //Buscando a referência do produto e cliente
        Produto produto = produtoDAO.buscarPorId(1L);
        Produto produto2 = produtoDAO.buscarPorId(2L);
        Produto produto3 = produtoDAO.buscarPorId(3L);
        Cliente cliente = clienteDAO.buscarPorId(1L);

        // Iniciar uma transação
        em.getTransaction().begin();

        // Adicionando a quantidade, quem é o pedido, e quem é o produto
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(40, pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(2, pedido, produto3));

        //Salvar/cadastrar em banco de dados
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);
        pedidoDAO.cadastrar(pedido2);

        // Commitar a transação
        em.getTransaction().commit();
        // Chamando o método que retorna a somatória do valor total vendido
        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
        System.out.println("Valor Total Vendido: " + totalVendido);

        // Imprimir um relatorio de vendas
        List<RelatorioVendasVO> relatorio = pedidoDAO.relatorioDeVendas();
        relatorio.forEach(System.out::println);

        // Fechar a transação
        em.close();
    }
}
