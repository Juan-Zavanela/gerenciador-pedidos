package service;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }

    public Optional<Produto> buscarPorId(int id) {
        return produtoDAO.buscarPorId(id);
    }

    public void salvar(Produto produto) {
        validarProduto(produto);
        produtoDAO.salvar(produto);
    }

    public void atualizar(Produto produto) {
        validarProduto(produto);
        produtoDAO.atualizar(produto);
    }

    public void deletar(int id) {
        produtoDAO.deletar(id);
    }

    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getMarca() == null || produto.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("Marca do produto é obrigatória.");
        }
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        if (produto.getGarantiaMeses() < 0) {
            throw new IllegalArgumentException("Garantia não pode ser negativa.");
        }
    }
}
