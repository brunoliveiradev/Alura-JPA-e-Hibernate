package loja.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
// mapeando Chave Composta
    @EmbeddedId
    private CategoriaId id;

    public Categoria(String nome) {
        this.id = new CategoriaId(nome, "teste");
    }

    public Categoria(CategoriaId id) {

        this.id = id;
    }

    public Categoria() {

    }

    public String getNome() {
        return this.id.getNome();
    }

}
