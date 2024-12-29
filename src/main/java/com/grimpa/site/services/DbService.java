package com.grimpa.site.services;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.Processo;
import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.enums.Modalidade;
import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Roles;
import com.grimpa.site.domain.enums.Status;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.repositories.ProcessoRepository;
import com.grimpa.site.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        tecAdmin.addPerfil(Perfil.TECNICO);
        tecAdmin.addRoles(Roles.ADMIN);

        Cliente clienteLPA = new Cliente(null, "Letícia Azevedo", "568.063.720-07", "leticiapazevedo@gmail.com", encoder.encode("123"));
        tecAdmin.addPerfil(Perfil.CLIENTE);
        tecAdmin.addRoles(Roles.USER);

        Processo processoBallet = new Processo(null, Modalidade.BALLET, Status.ATIVO, "Treinamento intermediário", "Escola Studio de Dança", clienteLPA, tecAdmin);

        tecnicoRepository.saveAll(List.of(tecAdmin));
        clienteRepository.saveAll(List.of(clienteLPA));
        processoRepository.saveAll(List.of(processoBallet));
    }

    public void startDbPostgreSql() {
        Tecnico tecAdmin1 = new Tecnico(null, "Tiago Menegaz", "942.595.920-09", "tmenegaz77@gmail.com", encoder.encode("123"));
        tecAdmin1.addPerfil(Perfil.TECNICO);
        tecAdmin1.addRoles(Roles.ADMIN);
//        tecAdmin1.setExcluido(Excluido.EXCLUIDO);

        Tecnico tecAdmin2 = new Tecnico(null, "Alexander Alenxandrov", "360.073.460-13", "alexander@gmail.com", encoder.encode("123"));
        tecAdmin2.addPerfil(Perfil.TECNICO);
        tecAdmin2.addRoles(Roles.USER);
//        tecAdmin2.setExcluido(Excluido.NAO_EXCLUIDO);

        Tecnico tecProfessor1 = new Tecnico(null, "Alphonse Polin", "429.354.690-11", "alphonsepolin@gmail.com", encoder.encode("123"));
        tecProfessor1.addPerfil(Perfil.TECNICO);
        tecProfessor1.addRoles(Roles.USER);
//        tecProfessor1.setExcluido(Excluido.NAO_EXCLUIDO);

        Tecnico tecProfessor3 = new Tecnico(null, "Tiago Polin", "171.086.650-00", "tiagopolin@gmail.com", encoder.encode("123"));
        tecProfessor3.addPerfil(Perfil.TECNICO);
        tecProfessor3.addRoles(Roles.USER);
//        tecProfessor3.setExcluido(Excluido.NAO_EXCLUIDO);

        Tecnico tecProfessor2 = new Tecnico(null, "Gilmar Sampaio", "964.348.350-94", "gilmarsampaio@gmail.com", encoder.encode("123"));
        tecProfessor2.addPerfil(Perfil.TECNICO);
        tecProfessor2.addRoles(Roles.USER);
//        tecProfessor2.setExcluido(Excluido.NAO_EXCLUIDO);

        Cliente clienteLPA = new Cliente(null, "Letícia Azevedo", "504.711.370-14", "leticiapazevedo@gmail.com", encoder.encode("123"));
        clienteLPA.addPerfil(Perfil.CLIENTE);
        clienteLPA.addRoles(Roles.USER);

        Cliente clienteSC = new Cliente(null, "Sinara Costa", "752.285.180-70", "sinaracosta@gmail.com", encoder.encode("123"));
        clienteSC.addPerfil(Perfil.CLIENTE);
        clienteSC.addRoles(Roles.USER);

        Cliente clienteT = new Cliente(null, "Letícia Outra", "123.456.789-09", "leticiaoutra@gmail.com", encoder.encode("123"));
        clienteT.addPerfil(Perfil.CLIENTE);
        clienteT.addRoles(Roles.USER);

        Processo processoBalletLPA = new Processo(null, Modalidade.BALLET, Status.ATIVO, "Treinamento intermediário", "Escola Studio de Dança", clienteLPA, tecAdmin1);
        Processo processoBalletSC = new Processo(null, Modalidade.BALLET, Status.PAUSADO, "Treinamento avançado", "Escola Studio de Dança", clienteSC, tecAdmin2);
        Processo processoContempLPA = new Processo(null, Modalidade.CONTEMPORANEO, Status.ATIVO, "Treinamento avançado", "Escola Studio de Dança", clienteLPA, tecProfessor1);
        Processo processoConempSC = new Processo(null, Modalidade.CONTEMPORANEO, Status.INATIVO, "Treinamento avançado", "Escola Studio de Dança", clienteSC, tecProfessor2);
        Processo processoConempT = new Processo(null, Modalidade.ALONGAMENTO, Status.ATIVO, "Treinamento avançado", "Escola Studio de Dança", clienteT, tecProfessor3);

        tecnicoRepository.saveAll(Arrays.asList(tecAdmin1, tecProfessor1, tecProfessor3));
        tecnicoRepository.saveAll(Arrays.asList(tecAdmin2, tecProfessor2));
        clienteRepository.saveAll(Arrays.asList(clienteLPA, clienteSC, clienteT));
        processoRepository.saveAll(Arrays.asList(
                processoBalletLPA,
                processoBalletSC, processoContempLPA, processoConempSC, processoConempT));
    }
}
