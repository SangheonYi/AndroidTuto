package myapp.co.bot.ui.home;

import java.util.ArrayList;

public class FoodMenu {
    private String name;
    private String sideDish;
    private String price;

    public FoodMenu(String name, String sideDish, String price){
        this.name = name;
        this.sideDish = sideDish;
        this.price = price;
    }

    //getter
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSideDish() {
        return sideDish;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSideDish(String sideDish) {
        this.sideDish = sideDish;
    }

    public static ArrayList setGetFoodMenus(String[] names, String[] sideDishes, String[] prices) {
        ArrayList foodMenus = new ArrayList();
        for (int i = 0; i < names.length-1; i++) {
            foodMenus.add(new FoodMenu(names[i], sideDishes[i], prices[i]));
        }
        return foodMenus;
    }
}
