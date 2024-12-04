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

        // INITIAL HANDSHAKE
        ObjectMapper objectMapper = new ObjectMapper();

        ReturnStoresQueryParameter param1 = new ReturnStoresQueryParameter(1, 1);

        try{
            String param1Json = objectMapper.writeValueAsString(param1);

            String response1Json = Request.post("http://localhost:8080/returnStores")
                    .bodyString(param1Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Store> response1 = JsonUtilities.readJsonString(response1Json, Store[].class);

            for (Store store : response1){
                System.out.println("storeid = " + store.getStoreId() + ", storename = " + store.getStoreName() + ", storelogo = " + store.getStoreLogo());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        ReturnCategoriesQueryParameter param2 = new ReturnCategoriesQueryParameter(1);
        try{
            String param2Json = objectMapper.writeValueAsString(param2);

            String response2Json = Request.post("http://localhost:8080/returnCategories")
                    .bodyString(param2Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Category> response2 = JsonUtilities.readJsonString(response2Json, Category[].class);

            for (Category category : response2){
                System.out.println("storeid = " + category.getStore().getStoreId() + ", category name = " + category.getCategoryName());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        ReturnHousematesQueryParameter param3 = new ReturnHousematesQueryParameter(1);
        try{
            String param3Json = objectMapper.writeValueAsString(param3);

            String response3Json = Request.post("http://localhost:8080/returnHousemates")
                    .bodyString(param3Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            List<Housemate> response3 = JsonUtilities.readJsonString(response3Json, Housemate[].class);

            for (Housemate housemate : response3){
                System.out.println("housemate id = " + housemate.getHousemateId() + ", housemate forename = " + housemate.getHousemateForename() + ", housemate surname = " + housemate.getHousemateSurname() + ", housemate img = " + housemate.getHousemateImg() + ", house id = " + housemate.getHouse().getHouseId());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        ReturnBasketIdQueryParameter param4 = new ReturnBasketIdQueryParameter(1,1);
        try{
            String param4Json = objectMapper.writeValueAsString(param4);

            String response4Json = Request.post("http://localhost:8080/returnBasket")
                    .bodyString(param4Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            ReturnedBasket response4 = objectMapper.readValue(response4Json, ReturnedBasket.class);

            System.out.println("basket id = " + response4.getBasketid());
            for (BasketItem basketItem : response4.getBasketItems()){
                System.out.println("basket item id = " + basketItem.getBasketItemId() + ", basket id = " + basketItem.getBasket().getBasketId() + ", store id = " + basketItem.getStore().getStoreId() + ", item id = " + basketItem.getItem().getItemId() + ", housemate id = " + basketItem.getHousemate().getHousemateId() + ", item quantity = " + basketItem.getItemQuantity());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        AddToBasketQueryParameter param5 = new AddToBasketQueryParameter(1, 1, 1, 1, 15);
        try{
            String param5Json = objectMapper.writeValueAsString(param5);

            String response5Json = Request.post("http://localhost:8080/addToBasket")
                    .bodyString(param5Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response5 = objectMapper.readValue(response5Json, Boolean.class);

            if (response5){
                System.out.println("add item successful");
            }
            else{
                System.out.println("add item unsuccessful");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        RemoveFromBasketQueryParameter param6 = new RemoveFromBasketQueryParameter(1, 1, 1, 15);
        try{
            String param6Json = objectMapper.writeValueAsString(param6);

            String response6Json = Request.post("http://localhost:8080/removeFromBasket")
                    .bodyString(param6Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response6 = objectMapper.readValue(response6Json, Boolean.class);

            if (response6){
                System.out.println("remove item successful");
            }
            else{
                System.out.println("remove item unsuccessful");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\n");

        SubmitOrderQueryParameter param7 = new SubmitOrderQueryParameter(1);
        try{
            String param7Json = objectMapper.writeValueAsString(param7);

            String response7Json = Request.post("http://localhost:8080/submitOrder")
                    .bodyString(param7Json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            boolean response7 = objectMapper.readValue(response7Json, Boolean.class);

            if (response7){
                System.out.println("order submitted successfully");
            }
            else{
                System.out.println("order not successfully submitted");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
