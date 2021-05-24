package loja.Services;

import loja.DAO.ProdutoDAO;
import loja.model.Produto;
import loja.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class CadastroDeProduto {

    public static void main(String[] args) {
        Produto celular = new Produto();
        celular.setNome("Xiaomi Redmi");
        celular.setDescricao("Smartphone");
        celular.setPreco(new BigDecimal("800"));

        // Utiliza uma classe de suporte para pegar a conexão do Entity Manager
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO dao = new ProdutoDAO(em);

        //Com os frameworks, as transações, o commit e o close são feito por eles

        // Iniciar uma transação
        em.getTransaction().begin();
        //Fazer insert na tabela
        dao.cadastrar(celular);
        // Commitar a transação
        em.getTransaction().commit();
        // Fechar a transação
        em.close();
    }
}
