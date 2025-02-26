package Enviroment.Objects;

public class Clock {
    private long time;
    public Clock(){
        this.time = 0;
    }
    public long getTime(){
        return this.time/10000;
    }
    public void Update(){
        this.time ++;
    }

}
