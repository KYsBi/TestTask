package project;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

public class startProject {
    public static void main(String[] args) throws IOException {

        FileInputStream fis;
        Properties property = new Properties();

        Map<Integer, String> cordsMap = new HashMap<>();
        Map<Integer, String> searchWord = new HashMap<>();
        Map<Integer, String> lineWithAllInformation = new HashMap<>();
        int countOfSuitableResult = 0;

        Scanner in = new Scanner(System.in);
        System.out.println("Введите символ:");                                              //считывание символ введеный user
        String usersString = in.nextLine();

        try {
            fis = new FileInputStream("TestTask/src/main/resources/config.properties");
            property.load(fis);
            String columnFromProperties = property.getProperty("column");
            int columnForSearch = Integer.valueOf(columnFromProperties);

            String fileName = "C:/Users/airports.dat";
            Path path = Paths.get(fileName);
            Scanner scanner = new Scanner(path);

            long startTime = System.currentTimeMillis();

            scanner.useDelimiter(System.getProperty("line.separator"));
            while(scanner.hasNext()){

                String str1 = scanner.nextLine();                                       //считываем строку
                int z=0;                                                                //количество совпадений с введенной строкой

                String str = str1.replaceAll("\"", "");                                 //затем str1 парсится в str
                String[] word = str.split(",");


                for (int i = 0; i < word.length; i ++) {                                //
                    cordsMap.put(i, word[i]);                                           //выбирается  столбец по которому будет
                }                                                                       //осуществляться поиск
                String sum = cordsMap.get(columnForSearch-1);                           //

                for(int i=0;i < usersString.length();i++) {
                    if (sum.charAt(i) == usersString.charAt(i))
                        z++;
                    if (z==usersString.length()){
                        searchWord.put(countOfSuitableResult, sum);                      //записываем слова, которые устраивают нас
                        lineWithAllInformation.put(countOfSuitableResult, str1);         //записываем строки, которые содержат подходящие слова
                        countOfSuitableResult++;                                         //считаем количество подходящих вариантов
                    }
                }
            }scanner.close();
                                                                                         //сортируем cordsMap1
            Map<Integer, String> sorted = searchWord
                    .entrySet()
                    .stream()
                    .sorted(comparingByValue())
                    .collect(
                            toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                    LinkedHashMap::new));


            ArrayList<Integer> al = new ArrayList<Integer>();                                   //записываем cordsMap1 в ArrayList
            Iterator<Map.Entry<Integer, String>> iterator = sorted.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, String> entry = iterator.next();
                al.add(entry.getKey());
            }

            for(int i=0;i<countOfSuitableResult;i++){                                           //выводим подходяшие строки
                System.out.println(lineWithAllInformation.get(al.get(i)));
            }

            if(countOfSuitableResult>0)
                System.out.println("Количество найденных строк: " + countOfSuitableResult);
            else
                System.out.println("По вашему запросу ничего не найдено");

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Время, затраченное на поиск: " + endTime + "мс");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}