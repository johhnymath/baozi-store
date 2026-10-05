package com.baozistore.controller;

import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // Body: { "cliente": {"id": 1}, "produto": {"id": 1}, "quantidade": 3 }
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null
                || pedido.getProduto() == null || pedido.getProduto().getId() == null
                || pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Informe cliente.id, produto.id e quantidade (maior que zero).");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).orElse(null);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente nao encontrado.");
        }

        Produto produto = produtoRepository.findById(pedido.getProduto().getId()).orElse(null);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado.");
        }

        pedido.setId(null);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // opcional: atualiza apenas a quantidade
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido dados) {
        return pedidoRepository.findById(id).map(p -> {
            if (dados.getQuantidade() != null && dados.getQuantidade() > 0) {
                p.setQuantidade(dados.getQuantidade());
            }
            return ResponseEntity.ok(pedidoRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
