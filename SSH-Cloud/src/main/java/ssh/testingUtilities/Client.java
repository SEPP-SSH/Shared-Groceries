package ssh.testingUtilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import ssh.ReturnedBasket;
import ssh.entities.BasketItem;
import ssh.entities.Category;
import ssh.entities.Housemate;
import ssh.entities.Store;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.client5.http.fluent.Request;
import ssh.queryParameterWrappers.*;
import ssh.utilities.JsonUtilities;

import java.util.List;
import java.util.Map;


public class Client {
    public static void main(String[] args){
        // this should act as a test client to help work out the networking between SSH App and SSH Cloud
        
    }

    public static List<Store> returnStores(int houseId, int housemateId){
        ObjectMapper objectMapper = new ObjectMapper();

        ReturnStoresQueryParameter queryParam = new ReturnStoresQueryParameter(houseId, housemateId);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/returnStores")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Store> response = JsonUtilities.readJsonString(responseJson, Store[].class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Category> returnCategories(int storeId){
        ObjectMapper objectMapper = new ObjectMapper();

        ReturnCategoriesQueryParameter queryParam = new ReturnCategoriesQueryParameter(storeId);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/returnCategories")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Category> response = JsonUtilities.readJsonString(responseJson, Category[].class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Housemate> returnHousemates(int houseId){
        ObjectMapper objectMapper = new ObjectMapper();

        ReturnHousematesQueryParameter queryParam = new ReturnHousematesQueryParameter(houseId);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/returnHousemates")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Housemate> response = JsonUtilities.readJsonString(responseJson, Housemate[].class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ReturnedBasket returnBasketId(int houseId, int storeId){
        ObjectMapper objectMapper = new ObjectMapper();

        ReturnBasketIdQueryParameter queryParam = new ReturnBasketIdQueryParameter(houseId,storeId);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/returnBasket")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            ReturnedBasket response = objectMapper.readValue(responseJson, ReturnedBasket.class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addToBasket(int basketId, int storeId, int itemId, int housemateId, int quantity){
        ObjectMapper objectMapper = new ObjectMapper();

        AddToBasketQueryParameter queryParam = new AddToBasketQueryParameter(basketId, storeId, itemId, housemateId, quantity);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/addToBasket")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response = objectMapper.readValue(responseJson, Boolean.class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeFromBasket(int basketId, int itemId, int housemateId, int quantity){
        ObjectMapper objectMapper = new ObjectMapper();

        RemoveFromBasketQueryParameter queryParam = new RemoveFromBasketQueryParameter(basketId, itemId, housemateId, quantity);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/removeFromBasket")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response = objectMapper.readValue(responseJson, Boolean.class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean submitOrder(int basketId){
        ObjectMapper objectMapper = new ObjectMapper();

        SubmitOrderQueryParameter queryParam = new SubmitOrderQueryParameter(basketId);

        try{
            String queryParamJson = objectMapper.writeValueAsString(queryParam);

            String responseJson = Request.post("http://localhost:8080/submitOrder")
                    .bodyString(queryParamJson, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response = objectMapper.readValue(responseJson, Boolean.class);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
