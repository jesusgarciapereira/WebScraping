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
			Elements forms = doc.select("form"); // Seleccionar formularios
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
					break; // Salir del bucle después de mostrar 3 enlaces
				}
			}
			archivo.write("</ul>");

			// 3 primeras clases utilizadas
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
			if (!forms.isEmpty()) {
				archivo.write("<p>Sí</p>");
				// Mostrar detalles del formulario
				Element formulario = forms.first();
				archivo.write("<p>Formulario redirige a: " + formulario.attr("action") + " con método "
						+ formulario.attr("method") + "</p>");
			} else {
				archivo.write("<p>No</p>");
			}
			
			
			// Imágenes encontradas (mostrar las primeras 3 con un tamaño máximo de 120px de alto)
			archivo.write("<h2>Imágenes</h2>");
			archivo.write("<ul>");
			
			int imagenesMostradas = 0;

			for (Element imagen : imagenes) {
			    if (imagenesMostradas < 3) {
			        String src = imagen.attr("src");
			        String alt = imagen.attr("alt");

			        // Agregar la etiqueta de imagen con un tamaño máximo de 120px de alto
			        archivo.write("<li><img src=\"" + src + "\" alt=\"" + alt + "\" style=\"max-height: 120px;\"></li>");

			        imagenesMostradas++;
			    } else {
			        break; // Salir del bucle después de mostrar 3 imágenes
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
