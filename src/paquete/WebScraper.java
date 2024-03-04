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
			String url = "https://cadenaser.com/radio-sevilla/";

			// Realizar una solicitud HTTP para obtener el contenido de la página
			Document doc = Jsoup.connect(url).get();

			// Seleccionar los elementos de interés utilizando selectores CSS o expresiones
			// jQuery
			Elements title = doc.select("title");
			Elements titulos = doc.select("h2");
			Elements enlaces = doc.select("a");

			// Crear un archivo HTML
			BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados.html"));
			archivo.write("<!DOCTYPE html>");
			archivo.write("<html>");
			archivo.write("<head>");
			archivo.write("<meta charset=\"UTF-8\">");
			archivo.write("<title>Web Scraping de " + title.text() + "</title>");
			archivo.write("</head>");
			archivo.write("<body>");
			archivo.write("<h1>" + title.text() + "</h1>");
			// No sé si esta es la manera correcta
			archivo.write("<ul>");
				archivo.write("<li><a href=\"https://cadenaser.com/podcast/radio-sevilla/la-camara-de-los-balones/517/\">La Cámara de los Balones</a></li>");
				archivo.write("<li><a href=\"https://cadenaser.com/podcast/radio-sevilla/el-ambigu/1671/\">El Ambigú</a></li>");
				archivo.write("<li><a href=\"https://cadenaser.com/podcast/radio-sevilla/cruz-de-guia/1666/\">Cruz de Guía</a></li>");
			archivo.write("</ul>");
			
			archivo.write("<ul>");
			for (Element enlace : enlaces) {
			archivo.write("<li><a href=\"" + enlace.attr("href") + "\">" + enlace.text() + "</a></li>");
		}
			archivo.write("</ul>");
			// Escribir los resultados en el archivo HTML
			archivo.write("<ul>");
			for (Element titulo : titulos) {
				archivo.write("<li>" + titulo.text() + "</li>");
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
