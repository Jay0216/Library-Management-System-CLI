public class GuestStrategy implements FineStrategy {
    

    @Override
    public double calculateFine(long lateDays) {
        return lateDays * 100.0;
    }

}
