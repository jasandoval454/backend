package co.edu.usbcali.jasan.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class JasanJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasanJavaApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void abrirNavegador() {
		String url = "http://localhost:8080/buscar.html";
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI(url));
			} else {
				System.out.println("Abre manualmente: " + url);
			}
		} catch (Exception e) {
			System.out.println("No se pudo abrir el navegador automáticamente. Entra manualmente a: " + url);
		}
	}
}
