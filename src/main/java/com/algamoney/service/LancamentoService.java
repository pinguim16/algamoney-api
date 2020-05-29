package com.algamoney.service;

import com.algamoney.model.Lancamento;
import com.algamoney.model.Pessoa;
import com.algamoney.repository.LancamentoRepository;
import com.algamoney.repository.PessoaRepository;
import com.algamoney.repository.filter.LancamentoFilter;
import com.algamoney.repository.projection.ResumoLancamento;
import com.algamoney.service.exception.PessoaInesxistenteOuInativaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Lancamento getLancamento(Long codigo){
        Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
        return lancamento.isPresent() ? lancamento.get() : null;
    }

    public List<Lancamento> findAll(){
        return this.lancamentoRepository.findAll();
    }

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return this.lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return this.lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    public Lancamento saveorUpdate(Lancamento lancamento){
        validarPessoa(lancamento);
        Lancamento lancamentoSalvo = buscarLancamentoExistente(lancamento);

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
        return this.lancamentoRepository.save(lancamento);
    }

    private Lancamento buscarLancamentoExistente(Lancamento lancamento) {
        if(lancamento.getCodigo() != null){
            Optional<Lancamento> lancamentoSalvo = this.lancamentoRepository.findById(lancamento.getCodigo());
            if(!lancamentoSalvo.isPresent()){
                throw new IllegalArgumentException("Lançamento não existe");
            }
            return lancamentoSalvo.get();
        }
        return null;
    }

    private void validarPessoa(Lancamento lancamento) {
        if(lancamento.getPessoa().getCodigo() != null) {
            Optional<Pessoa> pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getCodigo());
            if (!pessoa.isPresent() || pessoa.get().isInativo()) {
                throw new PessoaInesxistenteOuInativaException();
            }
        }
    }

    public void deleteById(Long codigo){
        this.lancamentoRepository.deleteById(codigo);
    }

}
