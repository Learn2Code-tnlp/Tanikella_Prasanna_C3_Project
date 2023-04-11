import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime now = getCurrentTime();

        if (openingTime.isBefore(closingTime)) {
            return now.isAfter(openingTime) && now.isBefore(closingTime);
        }

        return now.isBefore(openingTime)
                ? now.isBefore(openingTime) && now.isBefore(closingTime)
                : now.isAfter(openingTime) && now.isAfter(closingTime);
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    // When items are added to the order, this method is invoked which takes the list of items added as a input parameter and
    // returns the total order price as output
    public int calculateOrderValue(List<String> order) {
        int totalOrderPrice = 0;
        for(String orderedItem : order){
            totalOrderPrice+= menu.stream().filter(menuItem -> orderedItem.equals(menuItem.getName()))
                    .map(Item::getPrice).mapToInt(Integer::intValue).sum();
        }
        return totalOrderPrice;
    }

}
