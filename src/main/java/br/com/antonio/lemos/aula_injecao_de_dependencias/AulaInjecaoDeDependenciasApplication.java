package br.com.antonio.lemos.aula_injecao_de_dependencias;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AulaInjecaoDeDependenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AulaInjecaoDeDependenciasApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(MigracaoUsuario migracaoUsuario){
		return args -> {
			migracaoUsuario.migrar();
		};
	}
}

record User(String email, String username, String password) {

}

interface Reader<T> {
	List<T> read();
}

interface Writer<T> {
	void write(List<T> itens);
}

@Component
class MigracaoUsuario {
	Reader<User> reader;
	Writer<User> writer;

	public MigracaoUsuario(Reader<User> reader, Writer<User> writer) {
		this.reader = reader;
		this.writer = writer;
	}

	void migrar() {
		List<User> users = reader.read();
		writer.write(users);
	}
}

@Component
class FileReader implements Reader<User> {
	public List<User> read() {
		System.out.println("Lendo Usuarios do arquivo...");
		return List.of(new User("user@email", "user01", "123"));
	}
}

@Component
class BdWriter implements Writer<User> {
	public void write(List<User> users) {
		System.out.println("Escrevendo usuários no banco...");
		System.out.println(users);
	}
}