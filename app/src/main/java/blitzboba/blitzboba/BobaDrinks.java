package blitzboba.blitzboba;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rodrigo on 4/2/2017.
 */

public class BobaDrinks {

    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("gbOnly")
    public Boolean gbOnly;
    @SerializedName("id")
    public String id;
    @SerializedName("largePrice")
    public long largePrice;
    @SerializedName("soldOut")
    public Boolean soldOut;
    @SerializedName("vegan")
    public Boolean vegan;
    @SerializedName("smallPrice")
    public long smallPrice;
    @SerializedName("type")
    public String type;


    public BobaDrinks () {

    }

    public BobaDrinks(String name, String description, Boolean gbOnly, String id,
                      long largePrice, Boolean soldOut, Boolean vegan, long smallPrice, String type) {
        this.name = name;
        this.description = description;
        this.gbOnly = gbOnly;
        this.id = id;
        this.largePrice = largePrice;
        this.soldOut = soldOut;
        this.vegan = vegan;
        this.smallPrice = smallPrice;
        this.type = type;
    }
    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public Boolean isGbOnly() {
        return gbOnly;
    }

    public void setGbOnly(Boolean gbOnly) {
        this.gbOnly = gbOnly;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public long getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(long largePrice) {
        this.largePrice = largePrice;
    }

    @Exclude
    public Boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    @Exclude
    public Boolean isVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    @Exclude
    public long getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(long smallPrice) {
        this.smallPrice = smallPrice;
    }

    @Exclude
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
