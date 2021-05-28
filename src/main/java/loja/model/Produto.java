package loja.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_produtos")
@NamedQuery(name = "Produto.produtosPorCategoria",
        query = "SELECT p FROM Produto AS p WHERE p.categoria.id.nome = :nome")
@Inheritance(strategy = InheritanceType.JOINED)
public class Produto {
//mapeamento da entidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tipo de geração de chave primaria pelo banco
    private Long Id;
    private String nome;
//    @Column(name = "nomeDaTabelaCasoSejaDiferenteDaVariável")
    private String descricao;
    private BigDecimal preco;
    private LocalDate dataCadastro = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public Produto() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
