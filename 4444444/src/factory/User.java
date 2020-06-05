package factory;

public abstract class User {
    public abstract String getID();
    public abstract String getName();
    public abstract String getUserStatus();

    @Override
    public String toString(){
        return "ID= "+this.getID()+", Name="+this.getName()+", UserStatus="+this.getUserStatus();
    }
}
