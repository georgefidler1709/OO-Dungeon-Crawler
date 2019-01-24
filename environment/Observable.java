package environment;

public interface Observable {
    public void attach(FloorSwitch f);
    
    public void detach(FloorSwitch f);
    
    public void notifyObservers();

}
