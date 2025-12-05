public interface NotifySubject {


    void addObserver(NotifyObserver observer);
    void removeObserver(NotifyObserver observer);
    void notifyObservers(String message);

}
