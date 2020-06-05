package factory;


public class UserFactory {

    public static User getUser(String type, String id, String name, String status){
        if("FreeUSer".equalsIgnoreCase(type)) return new FreeUser(id, name, status);
        else if("RegularUser".equalsIgnoreCase(type)) return new RegularUser(id, name, status);
        else if("PremiumUser".equalsIgnoreCase(type)) return new PremiumUser(id, name, status);

        return null;
    }
}