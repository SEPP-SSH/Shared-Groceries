package ssh;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.eclipse.jetty.server.ServerConnector;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.handlers.CategoryHandler;
import ssh.queryParameterWrappers.*;
import ssh.supermarketAPIs.MoneyBurnerMarket;
import ssh.utilities.DatabaseExporter;
import ssh.utilities.JsonUtilities;

import java.util.List;
import java.util.Map;

import static ssh.utilities.JsonUtilities.readJsonString;

public class Main {
    public static void main(String[] args) {
        // populate database
        try{
            // wait for mysql server to spin up
            for (int counter = 0; counter < 25; counter++){
                System.out.println("Waiting for SSH Cloud's MySQL server to spin up. " + (25 - counter) + " seconds remaining.");
                Thread.sleep(1000);
            }
            
            MoneyBurnerMarket.populateSshDatabase();
            System.out.println("Populated initial database information successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }



        // create and start the server
        Javalin app = Javalin.create().start(8080);

        // instantiate a Jackson object mapper for object serialisation and deserialisation
        ObjectMapper objectMapper = new ObjectMapper();

        // define the requests that the server can receive
        app.post("/returnStores", ctx ->{
            ReturnStoresQueryParameter parameter = objectMapper.readValue(ctx.body(), ReturnStoresQueryParameter.class);

            List<Store> returnObject = QueryServicer.returnStores(parameter);

            ctx.json(returnObject);
        });
        app.post("/returnCategories", ctx ->{
            ReturnCategoriesQueryParameter parameter = objectMapper.readValue(ctx.body(), ReturnCategoriesQueryParameter.class);

            List<Category> returnObject = QueryServicer.returnCategories(parameter);

            ctx.json(returnObject);
        });
        app.post("/returnItems", ctx ->{
            ReturnItemsQueryParameter parameter = objectMapper.readValue(ctx.body(), ReturnItemsQueryParameter.class);

            List<Item> returnObject = QueryServicer.returnItems(parameter);

            ctx.json(returnObject);
        });
        app.post("/returnHousemates", ctx ->{
            ReturnHousematesQueryParameter parameter = objectMapper.readValue(ctx.body(), ReturnHousematesQueryParameter.class);

            List<Housemate> returnObject = QueryServicer.returnHousemates(parameter);

            ctx.json(returnObject);
        });
        app.post("/returnBasket", ctx ->{
            ReturnBasketIdQueryParameter parameter = objectMapper.readValue(ctx.body(), ReturnBasketIdQueryParameter.class);

            ReturnedBasket returnObject = QueryServicer.returnBasketId(parameter);

            ctx.json(returnObject);
        });
        app.post("/addToBasket", ctx ->{
            AddToBasketQueryParameter parameter = objectMapper.readValue(ctx.body(), AddToBasketQueryParameter.class);

            boolean returnObject = QueryServicer.addToBasket(parameter);

            ctx.json(returnObject);
        });
        app.post("/removeFromBasket", ctx ->{
            RemoveFromBasketQueryParameter parameter = objectMapper.readValue(ctx.body(), RemoveFromBasketQueryParameter.class);

            boolean returnObject = QueryServicer.removeFromBasket(parameter);

            ctx.json(returnObject);
        });
        app.post("/submitOrder", ctx ->{
            SubmitOrderQueryParameter parameter = objectMapper.readValue(ctx.body(), SubmitOrderQueryParameter.class);

            boolean returnObject = QueryServicer.submitOrder(parameter);

            ctx.json(returnObject);
        });

//        try {
//            // populates test data from the simulated supermarket API
//            MoneyBurnerMarket.populateSshDatabase();
//
//            // TESTING LINES - WILL BE REMOVED
//            List<Store> allStores = QueryServicer.returnStores(1,1);
//            for (Store store : allStores){
//                System.out.println("name of store: " + store.getStoreName());
//            }
//
//            List<Category> allCategories = QueryServicer.returnCategories(allStores.getFirst().getStoreId());
//            for (Category category : allCategories){
//                System.out.println("name of category : " + category.getCategoryName());
//            }
//
//            Map<Integer, List<BasketItem>> basket = QueryServicer.returnBasketId(1, 1);
//            System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
//            for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
//                System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
//            }
//
//            boolean addToBasketResult = QueryServicer.addToBasket(1, 1, 1, 1, 26);
//            if (addToBasketResult){
//                System.out.println("add to basket success");
//
//                basket.clear();
//                basket = QueryServicer.returnBasketId(1, 1);
//                System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
//                for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
//                    System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
//                }
//            }
//            else{
//                System.out.println("add to basket failure");
//            }
//
//            boolean removeFromBasketResult = QueryServicer.removeFromBasket(1, 1, 1, 35);
//            if (addToBasketResult){
//                System.out.println("add to basket success");
//
//                basket.clear();
//                basket = QueryServicer.returnBasketId(1, 1);
//                System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
//                for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
//                    System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
//                }
//            }
//            else{
//                System.out.println("add to basket failure");
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Exports database data to JSON files.
     */
    private static void exportDatabase(
            HouseHandler houseHandler,
            StoreHandler storeHandler,
            CategoryHandler categoryHandler,
            ItemHandler itemHandler) {
        try {
            // Initialize ObjectMapper
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // Ensure output directory exists
            java.io.File outputDir = new java.io.File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Export houses
            List<House> houses = houseHandler.getAll();
            mapper.writeValue(new java.io.File("output/house.json"), houses);

            // Export stores
            List<Store> stores = storeHandler.getAll();
            mapper.writeValue(new java.io.File("output/store.json"), stores);

            // Export categories
            List<Category> categories = categoryHandler.getAll();
            mapper.writeValue(new java.io.File("output/category.json"), categories);

            // Export items
            List<Item> items = itemHandler.getAll();
            mapper.writeValue(new java.io.File("output/item.json"), items);

            System.out.println("Data exported to JSON files successfully!");

        } catch (Exception e) {
            System.err.println("Error exporting database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}