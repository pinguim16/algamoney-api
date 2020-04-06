package com.algamoney.service;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {


    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento getLancamento(Long codigo){
        Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
        return lancamento.isPresent() ? lancamento.get() : null;
    }

    public List<Lancamento> findAll(){
        return this.lancamentoRepository.findAll();
    }

    public Lancamento saveorUpdate(Lancamento lancamento){
        return this.lancamentoRepository.save(lancamento);
    }
}
