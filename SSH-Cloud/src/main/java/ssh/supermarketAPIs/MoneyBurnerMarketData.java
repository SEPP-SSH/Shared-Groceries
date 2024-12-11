package ssh.supermarketAPIs;

public class MoneyBurnerMarketData {
    public static final String baskset = "[\n" +
            "  {\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"house\" : {\n" +
            "      \"house_id\": 1\n" +
            "    }\n" +
            "  }\n" +
            "]";

    public static final String basketItem = "[\n" +
            "  {\n" +
            "    \"basket\" : {\n" +
            "      \"basket_id\": 1\n" +
            "    },\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"item\" : {\n" +
            "      \"item_id\": 1\n" +
            "    },\n" +
            "    \"housemate\" : {\n" +
            "      \"housemate_id\": 1\n" +
            "    },\n" +
            "    \"item_quantity\": 5\n" +
            "  },\n" +
            "  {\n" +
            "    \"basket\" : {\n" +
            "      \"basket_id\": 1\n" +
            "    },\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"item\" : {\n" +
            "      \"item_id\": 2\n" +
            "    },\n" +
            "    \"housemate\" : {\n" +
            "      \"housemate_id\": 1\n" +
            "    },\n" +
            "    \"item_quantity\": 10\n" +
            "  }\n" +
            "]";

    public static final String category = "[\n" +
            "  {\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"category_name\": \"Fruit\"\n" +
            "  }\n" +
            "]";

    public static final String house = "[\n" +
            "  { \"house_address\": \"10 Downing Street\" }\n" +
            "]";

    public static final String housemate = "[\n" +
            "  {\n" +
            "    \"housemate_forename\": \"Nathan\",\n" +
            "    \"housemate_surname\": \"Drake\",\n" +
            "    \"housemate_img\": \"nathan.png\",\n" +
            "    \"house\" : {\n" +
            "      \"house_id\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"housemate_forename\": \"Clara\",\n" +
            "    \"housemate_surname\": \"Oswald\",\n" +
            "    \"housemate_img\": \"clara.png\",\n" +
            "    \"house\" : {\n" +
            "      \"house_id\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"housemate_forename\": \"Mike\",\n" +
            "    \"housemate_surname\": \"Ross\",\n" +
            "    \"housemate_img\": \"mike.png\",\n" +
            "    \"house\" : {\n" +
            "      \"house_id\": 1\n" +
            "    }\n" +
            "  }\n" +
            "]";

    public static final String item = "[\n" +
            "  {\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"item_name\": \"Apples\",\n" +
            "    \"item_img\": \"https://nearlynakedveg.co.uk/cdn/shop/products/Depositphotos_246784090_S_720x.jpg?v=1681394329\",\n" +
            "    \"item_base_price\": 3.00,\n" +
            "    \"item_offer_price\": 2.50,\n" +
            "    \"is_item_in_stock\": true,\n" +
            "    \"category\" : {\n" +
            "      \"category_id\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"store\" : {\n" +
            "      \"store_id\": 1\n" +
            "    },\n" +
            "    \"item_name\": \"Bananas\",\n" +
            "    \"item_img\": \"https://www.bobtailfruit.co.uk/media/mageplaza/blog/post/4/2/42e9as7nataai4a6jcufwg.jpeg\",\n" +
            "    \"item_base_price\": 1.00,\n" +
            "    \"item_offer_price\": 1.00,\n" +
            "    \"is_item_in_stock\": true,\n" +
            "    \"category\" : {\n" +
            "      \"category_id\": 1\n" +
            "    }\n" +
            "  }\n" +
            "]";

    public static final String store = "[\n" +
            "  {\n" +
            "    \"store_name\": \"Tesco\",\n" +
            "    \"store_logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/b/b0/Tesco_Logo.svg/2560px-Tesco_Logo.svg.png\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"store_name\": \"Sainsbury's\",\n" +
            "    \"store_logo\": \"https://www.sainsburysforbusiness.co.uk/wp-content/uploads/2019/02/Sainsburys_Logo_Masterbrand_Orange_CMYK.jpg\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"store_name\": \"Aldi\",\n" +
            "    \"store_logo\": \"https://standfirst-designweek-production.imgix.net/uploads/2017/03/13124707/ALDI-New-Logo.jpg\"\n" +
            "  }\n" +
            "]";
}
