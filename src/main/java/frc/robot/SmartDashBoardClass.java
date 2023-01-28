package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashBoardClass <T>{
    private final String key;
    private T defaultValue;
    private boolean posted;



    public SmartDashBoardClass(String key, T defaultValue){
        this.key = key;
        this.defaultValue = defaultValue;
        SmartDashboardValues.addValue(this);
    }
    // public static Sendable toSendable(Object value){
    //     SendableBuilder builder = new SendableBuilder().;
    
    
    // }

    public String getKey() {
        return key;
    }
    // public T getValue(){
    //     return (T)SmartDashboard.getData(key);
    // }
    // public Double getValue(){
    //     return SmartDashboard.getNumber(this.key, (Double)this.defaultValue);
    // }
    // public T getValue(){
    //     if(this.defaultValue.getClass() == Double.class){
    //         return (T)SmartDashboard.getNumber(this.key, (Double)this.defaultValue);
    //     }
    // }

    public T getValue(){
        if(this.getDefaultValue().getClass() == Double.class){
            return (T)(Double)SmartDashboard.getNumber(this.getKey(), (double)this.getDefaultValue());
        }
        else if(this.getDefaultValue().getClass() == Boolean.class){
            return (T)(Boolean)SmartDashboard.getBoolean(this.getKey(), (boolean)this.getDefaultValue());
        }
        else if(this.getDefaultValue().getClass() == String.class){
            return (T)SmartDashboard.getString(this.getKey(), (String)this.getDefaultValue());
        }
        else if(this.getDefaultValue().getClass() == Boolean[].class){
            return (T)SmartDashboard.getBooleanArray(this.getKey(), (boolean[])this.getDefaultValue());
        }
        else if(this.getDefaultValue().getClass() == Double[].class){
            return (T)SmartDashboard.getNumberArray(this.getKey(), (double[])this.getDefaultValue());
        }
        else if(this.getDefaultValue().getClass() == String[].class){
            return (T)SmartDashboard.getStringArray(this.getKey(), (String[])this.getDefaultValue());
        }
        else if(Sendable.class.isAssignableFrom(this.getClass())){
            return (T)SmartDashboard.getData(this.getKey());
        }
        else{
            System.err.println("Not Able to post to SmartDashBoard");
            return null;
        }
    }
    // add for other classes but it works bitch
    public T getDefaultValue() {
        return defaultValue;
    }
    public boolean getPosted(){ 
        return this.posted;
    }
    public void post(){
        this.posted = true;
    }
    public void setDefaultValue(T value){
        this.defaultValue = value;
    }
    public void jsonSave(GsonSaver gsonSaver){
        gsonSaver.saveObject(this, this.key);
    }
    public SmartDashBoardClass<T> replace(){
        return new SmartDashBoardClass<T>(this.key, this.defaultValue);
    }
}

// class toSendable<T> implements Sendable{

//     private String key;
//     private final Object value;

//     public toSendable(String key, Object value){
//         this.value = value;
//         this.key = key;

//     }

//     @Override
//     public void initSendable(SendableBuilder builder) {
//         if(this.value.getClass() == double.class){
//             builder.addDoubleProperty(this.key, () -> (double)value, (Object value) -> this.value = value);
//         }
        
//     }
//     public Sendable getSendable(){
//         return this;
//     }

// }
