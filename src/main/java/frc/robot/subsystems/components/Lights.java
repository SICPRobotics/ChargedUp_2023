package frc.robot.subsystems.components;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class Lights extends SubsystemBaseWrapper{

    int colorOfTheTick = 0;
    int colorOfTheTick2 = 0;
    int colorOfTheTick3 = 255;
    int colorState = 0;
    AddressableLED mLed;
    AddressableLEDBuffer mLedBuffer;


    public Lights(int port, int length){
        mLed = new AddressableLED(port);
        mLedBuffer = new AddressableLEDBuffer(length);
        mLed.setLength(mLedBuffer.getLength());

        mLed.setData(mLedBuffer);
        mLed.start();
        
    }

    public void setColor(int r, int g, int b){
        for(int i = 0; i < mLedBuffer.getLength(); i++){
              mLedBuffer.setRGB(i, r, g, b);
        }
        mLed.setData(mLedBuffer);
    }

    public void rainbow(){
        if(colorOfTheTick > 256){
            colorOfTheTick = colorOfTheTick - 256;
          }
          if(colorOfTheTick2 > 256){
            colorOfTheTick = colorOfTheTick - 256;
          }
          if(colorOfTheTick3 > 256){
            colorOfTheTick = colorOfTheTick - 256;
          }
          
          if(colorState == 0){
            for(int i = 0; i < mLedBuffer.getLength(); i++){
              mLedBuffer.setRGB(i, colorOfTheTick, colorOfTheTick2, colorOfTheTick3);
            }
            colorOfTheTick3--;
            colorOfTheTick++;
            if(colorOfTheTick >= 255){
              colorState = 1;
            }
          }
          if(colorState == 1){
            for(int i = 0; i < mLedBuffer.getLength(); i++){
              mLedBuffer.setRGB(i, colorOfTheTick, colorOfTheTick2, colorOfTheTick3);
            }
            colorOfTheTick --;
            colorOfTheTick2 ++;
            if(colorOfTheTick2 >= 255){
              colorState = 2;
            }
          }
          if(colorState == 2){
            for(int i = 0; i < mLedBuffer.getLength(); i++){
              mLedBuffer.setRGB(i, colorOfTheTick, colorOfTheTick2, colorOfTheTick3);
            }
            colorOfTheTick2 --;
            colorOfTheTick3 ++;
            if(colorOfTheTick3 >= 255){
              colorState = 0;
            }
          }
      
          mLed.setData(mLedBuffer);
    }

    public void rainbowLine(){
        for(int i = 0; i < mLedBuffer.getLength(); i++){
          if(i%20 == 0){
            rainbowMaker();
          }
          
          mLedBuffer.setRGB(i, colorOfTheTick, colorOfTheTick2, colorOfTheTick3);
        }
    
        mLed.setData(mLedBuffer);
    }

    void rainbowMaker(){
      if(colorOfTheTick > 256){
        colorOfTheTick = colorOfTheTick - 256;
      }
      if(colorOfTheTick2 > 256){
        colorOfTheTick = colorOfTheTick - 256;
      }
      if(colorOfTheTick3 > 256){
        colorOfTheTick = colorOfTheTick - 256;
      }
      
      if(colorState == 0){
        colorOfTheTick3 = colorOfTheTick3 - 1;
        colorOfTheTick = colorOfTheTick + 1;
        if(colorOfTheTick >= 255){
          colorState = 1;
        }
      }
      if(colorState == 1){
        colorOfTheTick = colorOfTheTick - 1;
        colorOfTheTick2 = colorOfTheTick2 + 1;
        if(colorOfTheTick2 >= 255){
          colorState = 2;
        }
      }
      if(colorState == 2){
        colorOfTheTick2 = colorOfTheTick2 - 1;
        colorOfTheTick3 = colorOfTheTick3 + 1;
        if(colorOfTheTick3 >= 255){
          colorState = 0;
        }
      }
    }
}