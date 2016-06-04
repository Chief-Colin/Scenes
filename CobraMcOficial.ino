#include <Smartcar.h>
#include <Servo.h>
#include<Wire.h>
/*
 * Full Serial control via bluetooth, the car will only move by rotating it after encoutering an obstacle 
 */
Car car;
Odometer encoderLeft, encoderRight;
Gyroscope gyro;
SR04 ultrasonicSensor;
SR04 ultrasonicSensor2;
//int speed,front,back, angle;
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 5; //D5
char in;
Servo firstServo;
Servo secondServo;
int servoPosition;
int throttle;
int deg;
int degServo;
int whichWay;
int howFast;

int rtPos;
int lnPos;
 
void setup() {
 // ultrasonicSensor2.attach(TRIGGER_PIN, ECHO_PIN);
  Serial3.begin(9600);
  Serial.begin(9600);
  gyro.attach();
  encoderLeft.attach(2);
  encoderRight.attach(3);
  encoderLeft.begin();
  encoderRight.begin();
  gyro.begin();
  car.begin(encoderLeft, encoderRight, gyro);
 ultrasonicSensor2.attach(9, 10);
  ultrasonicSensor.attach(6,5);
  pinMode(46, OUTPUT);
  firstServo.attach(A9);
  secondServo.attach(A8);
  in=0;


}


void loop() {
  
 
  

  
if(Serial3.available()){
  
in=Serial3.read();
//lights();
handleInput();
//Serial.print("ULTRASONIC: "+ultrasonicSensor.getDistance());
//Serial.print("ULTRASONIC2: "+ultrasonicSensor2.getDistance());
firstServo.write(topServo());
secondServo.write(downServo());

 

}
int dist = ultrasonicSensor.getDistance();
 if (dist > 0 && dist < 15){
  
 
   car.setSpeed(0);
   
 }
  
}

void lights(){
   digitalWrite(46, HIGH);   // turn the LED on (HIGH is the voltage level)
  delay(1000);              // wait for a second
  digitalWrite(46, LOW);    // turn the LED off by making the voltage LOW
  delay(1000);  
  }



void handleInput() { //handle serial input if there is any
   Serial.print(in);
  howFast = getMove();
  whichWay = getDirection();
 car.setSpeed(howFast);
 car.setAngle(whichWay);
 


}




   int getDirection(){
    switch(in){
  case 'a': 
            throttle=15;
   
    break;

  case 'b': 
        throttle =30;   

    break;   

  case 'c': 
       throttle = 45;
  
    break;

  case 'd':  
              throttle = 60;
    break;


    case 'e':  
              throttle =75;

    break;

 case 'f': 
          throttle =90;
 break;

 case 'g': 
        throttle = 105;
 break;

 case 'h':  
              throttle =120;

    break;

 case 'i': 
          throttle =135;
 break;

 case 'j': 
        throttle = 150;
 break;
 case 'k':  
              throttle =165;

    break;

 case 'l': 
          throttle =180;
 break;


case 'm': 
            throttle=-15;
   
    break;

  case 'n': 
        throttle =-30;   

    break;   

  case 'o': 
       throttle = -45;
  
    break;

  case 'p':  
              throttle = -60;
    break;


    case 'q':  
              throttle =-75;

    break;

 case 'r': 
          throttle =-90;
 break;

 case 's': 
        throttle = -105;
 break;

 case 't':  
              throttle =-120;

    break;

 case 'u': 
          throttle =-135;
 break;

 case 'v': 
        throttle =- 150;
 break;
 case 'w':  
              throttle =-165;

    break;

 case 'x': 
          throttle =-180;
 break;
 
 case '>':  
              throttle =75;

    break;

 case '<': 
          throttle =-75;
 break;
 
    }
    return throttle;
 }

 int getMove(){
  switch (in){
                 case  '!':
                 deg=10;
                   break;

                
               case '#':
               deg=20;
               break;

                
                case '-':
                deg=30;
                break;

                
               case '+':
               deg= 40;
               break;

                
                 case '?':
                 deg= 50;
                 break;

                
                 case '|':
                 deg =60;
                 break;

                
                case '&':
                deg = 70;
                break;
                
                case '@':
                deg = 80;
                break;
                
                
                case ')':
                deg = 90;
                break;

                
                case '(':
                deg = 100;
                break;

                 case '=':
                deg = 0;
                break;
                  
               case '1':
               deg=-100;
               break;
               
                case '2':
               deg=-90;
               break;

                case '3':
                deg=-80;
                break;

                
               case '4':
               deg= -70;
               break;

                
                 case '5':
                 deg= -60;
                 break;

                
                 case '6':
                 deg =-50;
                 break;

                
                case '7':
                deg = -40;
                break;
                
                case '8':
                deg = -30;
                break;
                
                
                case '9':
                deg = -20;
                break;

                
                case '0':
                deg = -10;
                break;


                }
  return deg;
 }

int  downServo(){
    switch(in){
      
       case  'A':
                 lnPos=175;
                   break;
                   
                     case  '}':
                 lnPos=165;
                   break;
                   
                     case  'B':
                 lnPos=155;
                   break;

                
               case 'C':
               lnPos=145;
               break;

                case 'O':
               lnPos=135;
               break;

                case 'D':
               lnPos=130;
               break;

                
                case '[':
                lnPos=125;
                break;

                 case 'F':
                lnPos=115;
                break;

                 case 'J':
                lnPos=110;
                break;

                               
               case 'Q':
               lnPos= 105;
               break;
               
               case 'W':
               lnPos= 105;
               break;

                
                 case 'E':
                 lnPos=100 ;
                 break;

                 case 'R':
                 lnPos=95 ;
                 break;

                 case 'Y':
                 lnPos=85 ;
                 break;


  
                
                 case 'U':
                 lnPos =80;
                 break;

                  case 'S':
                 lnPos =75;
                 break;

                  case 'X':
                 lnPos =65;
                 break;
                 
                 case 'Z':
                 lnPos =55;
                 break;

                  case 'V':
                 lnPos =50;
                 break;

                  case 'H':
                 lnPos =45;
                 break; 
              
      }
    return lnPos;
    }

   
   int topServo(){
    switch(in){
      //underScore
        case 'I':
                rtPos = 40;
                break;

                 case '*':
                rtPos = 50;
                break;

                 case 'G':
                rtPos = 60;
                break;
                
                case 'L':
                rtPos = 65;
                break;

                  case 'P':
                rtPos = 70;
                break;


                  case 'K':
                rtPos = 80;
                break;

                  case 'M':
                rtPos = 90;
                break;

              
                
        }
        return rtPos;
      
      
      }

