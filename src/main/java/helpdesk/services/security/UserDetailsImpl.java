package helpdesk.services.security;

import helpdesk.models.Pessoa;
import helpdesk.repositories.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService{

    private final PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        if(pessoa.isPresent()) {
            return new UserDetail(pessoa.get().getId(), pessoa.get().getEmail(), pessoa.get().getSenha(), pessoa.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);
    }
}


