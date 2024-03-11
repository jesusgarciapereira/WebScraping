package paquete;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
			int contadorEnlaces = 0;
			for (Element enlace : enlaces) {
				if (contadorEnlaces < 3) {
					archivo.write("<li><a href=\"" + enlace.attr("href") + "\">" + enlace.text() + "</a></li>");
					contadorEnlaces++;
				} else {
					break;
				}
			}
			archivo.write("</ul>");

			// Las 3 primeras clases utilizadas
			archivo.write("<h2>Clases</h2>");
			archivo.write("<ul>");
			int contadorClases = 0;
			for (Element clase : clases) {
				if (!clase.className().equals("") && contadorClases < 3) {
					archivo.write("<li>" + clase.attr("class") + "</li>");
					contadorClases++;
				}
			}
			archivo.write("</ul>");

			// Indicar si existe formulario y mostrar información
			archivo.write("<h2>Formulario</h2>");
			if (formularios.size() > 0) {
				archivo.write("<p>Sí</p>");
				archivo.write("<ul>");
				for (Element formulario : formularios) {
					archivo.write("<li>URL del formulario: " + formulario.attr("action"));
					archivo.write("</br>Método HTTP: " + formulario.attr("method") + "</li></br>");
				}
				archivo.write("</ul>");
			} else {
				archivo.write("<p>No</p>");
			}

			// Las 3 primeras imágenes encontradas
			archivo.write("<h2>Imágenes</h2>");
			archivo.write("<ul>");
			int imagenesMostradas = 0;
			for (Element imagen : imagenes) {
				if (imagenesMostradas < 3) {
					archivo.write("<li><img src=\"" + imagen.attr("src") + "\" alt=\"" + imagen.attr("alt")
							+ "\" style=\"max-height: 120px;\"></li>");
					imagenesMostradas++;
				} else {
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
