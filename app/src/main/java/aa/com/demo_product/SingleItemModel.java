package aa.com.demo_product;

/**
 * Created by asp_mac_5 on 14/05/18.
 */

public class SingleItemModel {


    private String name;
    private String description;
    private String price;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SingleItemModel() {
    }

    public SingleItemModel(String name, String description,String price,String url) {
        this.name = name;
        this.description = description;
        this.price=price;
        this.url=url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
