package io.pakcik.assignment.group.swipejer;

public class cards {
    private int itemId;
    private int userId;
    private String name,price;
    private byte[] image;
    private String description;
    //    private String profileImageUrl;
    public cards (int itemId, int userId, String name,String price, String description, byte[] image){
        this.itemId = itemId;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
//        this.profileImageUrl = profileImageUrl;
    }

    public int getItemId() { return this.itemId; }

    public int getUserId(){
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserID(String userID){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public void setName(String name){
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }


}