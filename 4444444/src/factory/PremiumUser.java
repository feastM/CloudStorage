package factory;

public class PremiumUser extends User {
    private String id;
    private String name;
    private String status;

    public PremiumUser(String id, String name, String status){
        this.id=id;
        this.name=name;
        this.status=status;
    }
    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUserStatus() {
        return this.status;
    }




}
