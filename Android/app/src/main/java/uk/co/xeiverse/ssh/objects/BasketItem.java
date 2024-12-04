package uk.co.xeiverse.ssh.objects;

public class BasketItem extends GroceryItem {

    private Integer quantity;
    private Integer userId;

    public BasketItem(
            Integer id,
            String name,
            String imgUrl,
            Double basePrice,
            Double offerPrice,
            Integer inStock,
            Integer category
    ) {
        super(id, name, imgUrl, basePrice, offerPrice, inStock, category);
    }

    public BasketItem(
            Integer id,
            String name,
            String imgUrl,
            Double basePrice,
            Double offerPrice,
            Integer inStock,
            Integer category,
            Integer quantity,
            Integer userId
    ) {
        super(id, name, imgUrl, basePrice, offerPrice, inStock, category);

        this.quantity = quantity;
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
