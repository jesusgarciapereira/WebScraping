package paquete;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
	public static void main(String[] args) {
		try {
			// URL de la página web que queremos scrapear
			String url = "https://www.antena3.com/";

			// Realizar una solicitud HTTP para obtener el contenido de la página
			Document doc = Jsoup.connect(url).get();

			// Seleccionar los elementos de interés utilizando selectores CSS o expresiones
			// jQuery
			Elements title = doc.select("title");
			Elements enlaces = doc.select("a");
			Elements clases = doc.select("*");
			Elements formularios = doc.select("form");
			Elements imagenes = doc.select("img");
			
			// Contadores para extraer un número determinado de elementos
			int contador = 0;
			
			// Crear un archivo HTML
			BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados.html"));
			archivo.write("<!DOCTYPE html>");
			archivo.write("<html>");
			archivo.write("<head>");
			archivo.write("<meta charset=\"UTF-8\">");
			// Title extraido de la propia página
			archivo.write("<title>Web Scraping de " + title.text() + "</title>");
			archivo.write("</head>");
			archivo.write("<body>");
			// Título
			archivo.write("<h1>" + title.text() + "</a></h1>");

			// Los 3 primeros enlaces encontrados
			archivo.write("<h2>Enlaces</h2>");
			archivo.write("<ul>");
			// Bucle for-each que recorre todos los enlaces
			for (Element enlace : enlaces) {
				// Si el contador es menor que 3
				if (contador < 3) {
					// Te los muestra
					archivo.write("<li><a href=\"" + enlace.attr("href") + "\">" + enlace.text() + "</a></li>");
					// Incrementa el contador
					contador++;
				} else {
					// En caso contrario, salimos del bucle
					break;
				}
			}
			archivo.write("</ul>");

			// Las 3 primeras clases utilizadas
			archivo.write("<h2>Clases</h2>");
			archivo.write("<ul>");
			// Reiniciamos el contador
			contador = 0;
			// Bucle for-each que recorre todas las clases
			for (Element clase : clases) {
				// Si el nombre de la clase no es una cadena vacía y el contador es menor que 3
				if (!clase.className().equals("") && contador < 3) {
					// Te las muestra
					archivo.write("<li>" + clase.attr("class") + "</li>");
					// Incrementa el contador
					contador++;
				}
			}
			archivo.write("</ul>");

			// Indicar si existe formulario y mostrar información
			archivo.write("<h2>Formulario</h2>");
			// Si la cantidad de formularios es mayor que cero
			if (formularios.size() > 0) {
				// Indica que sí hay formularios
				archivo.write("<p>Sí</p>");
				archivo.write("<ul>");
				// Bucle for-each que recorre todos los formularios
				for (Element formulario : formularios) {
					// Y te los muestra, tanto la URL
					archivo.write("<li>URL del formulario: " + formulario.attr("action"));
					// Como el método
					archivo.write("</br>Método HTTP: " + formulario.attr("method") + "</li></br>");
				}
				archivo.write("</ul>");
				// En caso contrario indica que no hay formularios
			} else {
				archivo.write("<p>No</p>");
			}

			// Las 3 primeras imágenes encontradas
			archivo.write("<h2>Imágenes</h2>");
			archivo.write("<ul>");
			// Reiniciamos el contador
			contador = 0;
			// Bucle for-each que recorre todas las imágenes
			for (Element imagen : imagenes) {
				// Si el contador es menor que 3
				if (contador < 3) {
					// Te las muestra
					archivo.write("<li><img src=\"" + imagen.attr("src") + "\" alt=\"" + imagen.attr("alt")
							+ "\" style=\"max-height: 120px;\"></li>");
					contador++;
					// Incrementa el contador
				} else {
					// En caso contrario, salimos del bucle
					break;
				}
			}

			archivo.write("</ul>");
			archivo.write("</body>");
			archivo.write("</html>");

			// Cerrar el archivo
			archivo.close();

			System.out.println("Archivo HTML creado con éxito.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
