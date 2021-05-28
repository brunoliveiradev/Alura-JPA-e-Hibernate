package loja.Services;

import loja.DAO.PedidoDAO;
import loja.model.Pedido;
import loja.util.JPAUtil;
import loja.util.PopularBDUtil;

import javax.persistence.EntityManager;

public class PerformanceConsultas {

    public static void main(String[] args) {
        PopularBDUtil novo = new PopularBDUtil();
        novo.popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        Pedido buscarPedidoComCliente = pedidoDAO.buscarPedidoComCliente(1L);

        em.close();
        System.out.println(buscarPedidoComCliente.getCliente().getNome());
    }
}
