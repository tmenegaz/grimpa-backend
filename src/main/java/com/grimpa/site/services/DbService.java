package com.grimpa.site.services;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.Processo;
import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.enums.Modalidade;
import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Status;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.repositories.ProcessoRepository;
import com.grimpa.site.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DbService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void startDbH2() {
        Tecnico tecAdmin = new Tecnico(null, "Tiago Menegaz", "021.500.629-17", "tmenegaz77@gmail.com", encoder.encode("280577T@m"));
        tecAdmin.addPerfil(Perfil.ADMIN);

        Cliente clienteLPA = new Cliente(null, "Letícia Azevedo", "568.063.720-07", "leticiapazevedo@gmail.com", encoder.encode("123"));

        Processo processoBallet = new Processo(null, Modalidade.BALLET, Status.ATIVO, "Treinamento intermediário", "Escola Studio de Dança", clienteLPA, tecAdmin);

        tecnicoRepository.saveAll(Arrays.asList(tecAdmin));
        clienteRepository.saveAll(Arrays.asList(clienteLPA));
        processoRepository.saveAll(Arrays.asList(processoBallet));
    }

    public void startDbPostgreSql() {
        Tecnico tecAdmin = new Tecnico(null, "Tiago Menegaz", "021.500.629-17", "tmenegaz77@gmail.com", encoder.encode("280577T@m"));
        tecAdmin.addPerfil(Perfil.ADMIN);

        Tecnico tecProfessor = new Tecnico(null, "Alphonse Polin", "429.354.690-11", "alphonsepolin@gmail.com", encoder.encode("280577T@m"));

        Cliente clienteLPA = new Cliente(null, "Letícia Azevedo", "504.711.370-14", "leticiapazevedo@gmail.com", encoder.encode("123"));
        Cliente clienteSC = new Cliente(null, "Sinara Costa", "752.285.180-70", "sinaracosta@gmail.com", encoder.encode("123"));

        Processo processoBalletLPA = new Processo(null, Modalidade.BALLET, Status.ATIVO, "Treinamento intermediário", "Escola Studio de Dança", clienteLPA, tecAdmin);
        Processo processoBalletSC = new Processo(null, Modalidade.BALLET, Status.ATIVO, "Treinamento avançado", "Escola Studio de Dança", clienteSC, tecProfessor);
        Processo processoContempLPA = new Processo(null, Modalidade.CONTEMPORANEO, Status.ATIVO, "Treinamento avançado", "Escola Studio de Dança", clienteLPA, tecAdmin);

        tecnicoRepository.saveAll(Arrays.asList(tecAdmin, tecProfessor));
        clienteRepository.saveAll(Arrays.asList(clienteLPA, clienteSC));
        processoRepository.saveAll(Arrays.asList(processoBalletLPA, processoBalletSC, processoContempLPA));
    }
}
