package com.algamoney.repository;

import com.algamoney.model.Pessoa;
import com.algamoney.repository.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {
}
