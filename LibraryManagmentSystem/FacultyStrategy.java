public class FacultyStrategy implements FineStrategy {

    @Override
    public double calculateFine(long lateDays) {
        return lateDays * 20.0;
    }
    
}
