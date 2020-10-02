package PriceObjects;


import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FlightListApplication {


    public static void main(String[] args) {

//Raw List given for question
        String result = "[\n" +
                " {\n" +
                "   'destination': 'LGW',\n" +
                "   'origin': 'JFK',\n" +
                "   'arrival_time': '17:50',\n" +
                "   'departure_time': '18:00',\n" +
                "   'price': '432GBP',\n" +
                "   'source': 'duffel_air'\n" +
                " },\n" +
                " {\n" +
                "   'flights': 'JFK/LGW',\n" +
                "   'arrival_time': '17:50',\n" +
                "   'departure_time': '18:00',\n" +
                "   'price_fractional': '12300',\n" +
                "   'currency_code': 'GBP',\n" +
                "   'source': 'virgin',\n" +
                " },\n" +
                " {\n" +
                "   'flights': 'JFK/LGW',\n" +
                "   'arrival_time': '19:30',\n" +
                "   'departure_time': '15:00',\n" +
                "   'price_fractional': '10900',\n" +
                "   'currency_code': 'GBP',\n" +
                "   'source': 'virgin',\n" +
                " },\n" +
                " {\n" +
                "   'destination_code': 'lgw',\n" +
                "   'origin_code': 'jfk',\n" +
                "   'arrival_datetime': '2020-07-23 12:07:53 +0000',\n" +
                "   'departure_time': '2020-07-23 09:01:20 +0000',\n" +
                "   'price': {\n" +
                "     'code': 'USD',\n" +
                "     'value': '325.50'\n" +
                "   },\n" +
                "   'source':  'aa'\n" +
                " },\n" +
                " {\n" +
                "   'destination_code': 'lgw',\n" +
                "   'origin_code': 'jfk',\n" +
                "   'duration': '542',\n" +
                "   'take_off_at': '2020-07-23 09:01:20 +0000',\n" +
                "   'price': {\n" +
                "     'code': 'GBP',\n" +
                "     'value': '425.50'\n" +
                "   },\n" +
                "   'source': 'ba',\n" +
                " }\n" +
                "]";

        List<PriceObject> sortedPrice = new ArrayList<>();

        //Transform result in to workable json list
        JSONArray jsonArray = new JSONArray(result);

        //Application of rules dependent on source
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject sourceObjects = jsonArray.getJSONObject(i);
            String source = sourceObjects.get("source").toString();
            switch (source) {

                case "duffel_air":
                    double price = Double.parseDouble(sourceObjects.get("price").toString().replaceAll("[^0-9]+", " ").trim());
                    sortedPrice.add(new PriceObject(BigDecimal.valueOf(price), source, sourceObjects));
                    break;
                case "virgin":
                    double price_fractional = Double.parseDouble(sourceObjects.get("price_fractional").toString());
                    sortedPrice.add(new PriceObject(BigDecimal.valueOf((price_fractional / 100)), source, sourceObjects));
                    break;

                case "aa":
                    JSONObject p = sourceObjects.getJSONObject("price");
                    BigDecimal usdPrice = BigDecimal.valueOf(Double.parseDouble(p.get("value").toString()));
                    // we would need a converter to check the code key and apply the correct conversion
                    sortedPrice.add(new PriceObject(usdPrice.multiply(BigDecimal.valueOf(0.78)), source, sourceObjects));
                    break;

                case "ba":
                    JSONObject p2 = sourceObjects.getJSONObject("price");
                    BigDecimal ukprice = BigDecimal.valueOf(Double.parseDouble(p2.get("value").toString()));
                    sortedPrice.add(new PriceObject(ukprice, source, sourceObjects));
                    break;

                default:
                    //do nothing
            }
        }

        //Sort the prices in to the cheapest first
        List<PriceObject> cheapest = sortedPrice.stream().sorted(Comparator.comparing(PriceObject::getPrice)).collect(Collectors.toList());


        // Get the results back in to the format given in the beginning
        cheapest.forEach(i -> System.out.println(i.getOriginal()));


    }


}
