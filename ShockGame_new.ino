#define RELAY_ON 1
#define RELAY_OFF 0

int relayPins[] = { 
  2, 3, 4, 5, 6, 7, 8, 9 };       // an array of pin numbers to which relays are attached
int relayCount = 8;              // the number of pins (i.e. the length of the array)

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
  
  delay(4000); //Check that all relays are inactive at Reset
  Serial.println("Ready to start");
  
  //---( THEN set pins as outputs )----  
  // use a for loop to initialize each pin as an output:
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    pinMode(relayPins[thisPin], OUTPUT);      
  }
  
}//--(end setup )---

void loop() 
{
  pinsOff(); //make sure everything is still off
  if (Serial.available())
  {

    char ch = Serial.read();  //looking for characters on the serial port from the python script 
    int convertInt = ch - '1'; //easy way to convert char to int and get to array numbering
    pinsOn(convertInt); // function to turn on relays
    delay(100); //this is how long the shock lasts
    pinsOff();
  }
}


void pinsOn (int shockNumber){
for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    if (shockNumber == (thisPin))
    { //do nothing if this is the right number
    }
    else {
      pinMode(relayPins[thisPin], RELAY_ON);
    }
  }
}

void pinsOff(){
  for (int thisPin = 0; thisPin < relayCount; thisPin++)  {
    pinMode(relayPins[thisPin], RELAY_OFF);      
  }
}
