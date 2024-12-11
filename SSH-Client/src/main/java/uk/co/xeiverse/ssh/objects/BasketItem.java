package uk.co.xeiverse.ssh.objects;

public class BasketItem extends GroceryItem {

    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
