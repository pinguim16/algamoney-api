package com.algamoney.resource;

import com.algamoney.event.RecursoCriadoEvent;
import com.algamoney.model.Lancamento;
import com.algamoney.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    public ResponseEntity<?> getLancamento(@PathVariable("id") Long id){
        Lancamento lancamento = this.lancamentoService.getLancamento(id);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllLancamentos(){
        List<Lancamento> lancamentos = this.lancamentoService.findAll();
        return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = this.lancamentoService.saveorUpdate(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }
}
