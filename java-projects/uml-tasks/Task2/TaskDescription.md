# Task Description
### Given the Following Java Class "Robot", Create a UML Class Diagram to Represent It:
```
public class Robot
{
    private String model;
    private int batteryLife;
    private String purpose;
    public Robot(String model, int batteryLife, String purpose)
    {
        this.model = model;
        this.batteryLife = batteryLife;
        this.purpose = purpose;
    }
    public void performTask()
    {
        // Code for performing a task
    }
    public void recharge()
    {
        // Code for recharging
    }
    public boolean isFullyCharged()
    {
        // Return true if battery is full
        return batteryLife == 100;
    }
}
```
