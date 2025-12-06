public class StudentStrategy implements FineStrategy {

    @Override
    public double calculateFine(long lateDays) {
        return lateDays * 50.0;
    }
    
}
