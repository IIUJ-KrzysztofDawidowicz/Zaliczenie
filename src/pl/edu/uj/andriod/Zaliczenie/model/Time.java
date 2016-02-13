package pl.edu.uj.andriod.Zaliczenie.model;

public class Time {
    
    public final int hour;
    public final int minutes;
    
    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }
    
    public static Time parse(String time){
        String[] hourMinute = time.split(":");
        return new Time(Integer.parseInt(hourMinute[0]), Integer.parseInt(hourMinute[1]));
    } 
    
    public static Time tryParse(String time){
        if (time == null) {
            return null;
        }
        return parse(time);
    }
}
