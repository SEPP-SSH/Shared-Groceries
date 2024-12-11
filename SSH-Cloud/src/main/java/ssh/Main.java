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
            Thread.sleep(25000); // required to wait for mysql server to spin up
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
}