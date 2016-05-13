#define RELAY_ON 1
#define RELAY_OFF 0

int relayPins[] = { 
  2, 3, 4, 5 };       // an array of pin numbers to which relays are attached
int relayCount = 4;              // the number of pins (i.e. the length of the array)
boolean pinStatus = 0;

void setup()   /****** SETUP: RUNS ONCE ******/
{
 
  //-------( Initialize Pins so relays are inactive at reset)----
  // the array elements are numbered from 0 to (pinCount - 1).
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    pinMode(relayPins[thisPin], RELAY_OFF);      
  }

  // initialize serial:
  Serial.begin(9600);
  while (! Serial); // Wait until Serial is ready - Leonardo Arduino
  
  delay(2000); //Check that all relays are inactive at Reset
  Serial.println("Ready to start");
  
  //---( THEN set pins as outputs )----  
  // use a for loop to initialize each pin as an output:
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    pinMode(relayPins[thisPin], OUTPUT);      
  }
  
}//--(end setup )---

void loop() 
{

  if (Serial.available())
  { 
    char ch = Serial.read();  //looking for characters on the serial port
    int convertInt = ch - '1'; //easy way to convert char to int and get to array numbering
    if (pinStatus == 0 && 0 < convertInt < relayCount){
      pinStatus = 1;
      pinsOn(convertInt); // function to turn on relays
    }
    pinsOff();
  }
}


void pinsOn (int shockNumber){
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    Serial.print(thisPin);
      if (thisPin != shockNumber)
      { //Open relay on all but the correct pin
        pinMode(relayPins[thisPin], RELAY_ON);
      }
  }
  delay(100); //this is how long the shock lasts
}

void pinsOff(){
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    pinMode(relayPins[thisPin], RELAY_OFF);      
  }
}
