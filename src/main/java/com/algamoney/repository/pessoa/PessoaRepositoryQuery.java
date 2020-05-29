package com.algamoney.repository.pessoa;

import com.algamoney.model.Pessoa;
import com.algamoney.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

    Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
