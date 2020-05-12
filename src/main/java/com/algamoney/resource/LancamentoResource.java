package com.algamoney.resource;

import com.algamoney.event.RecursoCriadoEvent;
import com.algamoney.exceptionHandler.AlgamoneyExceptionHandler;
import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;
import com.algamoney.service.LancamentoService;
import com.algamoney.service.exception.PessoaInesxistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{codigo}")
    public ResponseEntity<?> getLancamento(@PathVariable("codigo") Long id){
        Lancamento lancamento = this.lancamentoService.getLancamento(id);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> pesquisar(LancamentoFilter lancamentoFilter){
        List<Lancamento> lancamentos = this.lancamentoService.findAll();
        return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = this.lancamentoService.saveorUpdate(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @ExceptionHandler({PessoaInesxistenteOuInativaException.class})
    public ResponseEntity<Object> handlerPessoaInesxistenteOuInativaException(PessoaInesxistenteOuInativaException ex){
        String mensagemUsuario = this.messageSource.getMessage("pessoa.inativa-ou-inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}
