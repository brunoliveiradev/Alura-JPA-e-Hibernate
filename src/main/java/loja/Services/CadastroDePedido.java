package loja.Services;

import loja.DAO.ClienteDAO;
import loja.DAO.PedidoDAO;
import loja.DAO.ProdutoDAO;
import loja.model.Cliente;
import loja.model.ItemPedido;
import loja.model.Pedido;
import loja.model.Produto;
import loja.util.PopularBDUtil;
import loja.util.JPAUtil;

import javax.persistence.EntityManager;

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
        Cliente cliente = clienteDAO.buscarPorId(1L);

        // Iniciar uma transação
        em.getTransaction().begin();

        // Adicionando a quantidade, quem é o pedido, e quem é o produto
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        //Salvar/cadastrar em banco de dados
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);

        // Commitar a transação
        em.getTransaction().commit();
        // Fechar a transação
        em.close();
    }
}
