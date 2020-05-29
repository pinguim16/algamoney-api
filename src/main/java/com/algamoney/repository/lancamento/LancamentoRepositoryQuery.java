package com.algamoney.repository.lancamento;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;
import com.algamoney.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter,Pageable pageable);

}
