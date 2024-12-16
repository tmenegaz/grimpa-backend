package com.grimpa.site.services;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.Processo;
import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.dtos.ProcessoDto;
import com.grimpa.site.domain.enums.Modalidade;
import com.grimpa.site.domain.enums.Status;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.repositories.ProcessoRepository;
import com.grimpa.site.repositories.TecnicoRepository;
import com.grimpa.site.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Processo findById(Integer id) {
        Optional<Processo> processo = repository.findById(id);
        return processo.orElseThrow(() -> new ObjectNotFoundException("Processo não encontrado! ID:" + id));
    }

    public List<Processo> findAll() {
        return repository.findAll();
    }

    public Processo create(ProcessoDto processoDto) {
        processoDto.setId(null);

        Optional<Cliente> cliente = clienteRepository.findById(processoDto.getCliente());
        Optional<Tecnico> tecnico = tecnicoRepository.findById(processoDto.getTecnico());

        Processo processo = new Processo();

        Processo statusProcesso = processo.toStatusProcesso(processoDto.getStatus());
        processo.setStatus(statusProcesso.getStatus());

        Processo modalidadeProcesso = processo.toModalidadeProcesso(processoDto.getModalidade());
        processo.setModalidade(modalidadeProcesso.getModalidade());

        processo.setTitulo(processoDto.getTitulo());
        processo.setObservacao(processoDto.getObservacao());

        processo.setCliente(cliente.get());
        processo.setTecnico(tecnico.get());

        return repository.save(processo);
    }

    public Processo update(Integer id, ProcessoDto processoDto) {
        processoDto.setId(id);
        Processo processoOld = this.findById(id);

        processoOld = new Processo();
        processoOld.setDataInicio(processoDto.getDataInicio());
        processoOld.setDataFim(processoDto.getDataFim());

        Processo statusProcesso = processoOld.toStatusProcesso(processoDto.getStatus());
        processoOld.setStatus(statusProcesso.getStatus());

        Processo modalidadeProcesso = processoOld.toModalidadeProcesso(processoDto.getModalidade());
        processoOld.setModalidade(modalidadeProcesso.getModalidade());

        processoOld.setTitulo(processoDto.getTitulo());
        processoOld.setObservacao(processoDto.getObservacao());

        Optional<Tecnico> tecnico = tecnicoRepository.findById(processoDto.getTecnico());
        processoOld.setTecnico(tecnico.get());

        Optional<Cliente> cliente = clienteRepository.findById(processoDto.getCliente());
        processoOld.setCliente(cliente.get());

        return repository.save(processoOld);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
