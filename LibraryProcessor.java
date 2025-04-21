import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class LibraryProcessor {

    public static void main(String[] args) {
        try {
            // Чтение XML-файла
            File xmlFile = new File("library.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Нормализация структуры документа
            document.getDocumentElement().normalize();

            // Получение всех книг
            NodeList bookList = document.getElementsByTagName("book");
            double totalPrice = 0;
            int bookCount = bookList.getLength();

            System.out.println("Список книг:");
            for (int i = 0; i < bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;

                    // Чтение данных книги
                    String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
                    int year = Integer.parseInt(bookElement.getElementsByTagName("year").item(0).getTextContent());
                    String genre = bookElement.getElementsByTagName("genre").item(0).getTextContent();
                    double price = Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent());
                    totalPrice += price;

                    // Вывод данных книги
                    System.out.printf("%s | %s | %d | %s | %.2f%n", title, author, year, genre, price);
                }
            }

            // Вычисление средней цены
            double averagePrice = totalPrice / bookCount;
            System.out.printf("%nСредняя цена книг: %.2f%n", averagePrice);

            // Фильтрация по жанру
            String filterGenre = "Пересказ Сумерек от лица Эдварда Каллена";
            System.out.printf("%nКниги жанра %s:%n", filterGenre);
            for (int i = 0; i < bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;
                    String genre = bookElement.getElementsByTagName("genre").item(0).getTextContent();
                    if (genre.equals(filterGenre)) {
                        String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                        System.out.println(title);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}