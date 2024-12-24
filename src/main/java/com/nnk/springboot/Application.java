package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée principal de l'application Spring Boot.
 * <p>
 * Cette classe démarre l'application en initialisant le contexte Spring et en lançant le serveur embarqué.
 * Elle utilise l'annotation {@link SpringBootApplication}, qui encapsule :
 * <ul>
 *     <li>{@code @Configuration} : Indique que la classe est une source de configuration Spring.</li>
 *     <li>{@code @EnableAutoConfiguration} : Active la configuration automatique de Spring Boot.</li>
 *     <li>{@code @ComponentScan} : Permet de scanner les composants Spring dans le package courant et ses sous-packages.</li>
 * </ul>
 */
@SpringBootApplication
public class Application {

	/**
	 * Méthode principale pour démarrer l'application.
	 * <p>
	 * Cette méthode appelle {@link SpringApplication#run(Class, String...)} pour initialiser et exécuter l'application.
	 *
	 * @param args Les arguments de ligne de commande (facultatifs).
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Application Spring Boot démarrée avec succès !");
	}
}
