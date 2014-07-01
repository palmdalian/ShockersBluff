ShockersBluff
=============
Code from this video:
https://www.youtube.com/watch?v=WKmOH_-RwCs

This is setup to run on a Mac...might need tweaking for Linux and definitely needs work for Windows. To ensure everyone gets the same shock, it's easiest to splice off the wires from the TENS unit and run each positive/negative to the relays. Don't attach multiple pads to one person if you're doing this!

Some code copied / modified from these sources:
http://arduino.cc/en/Tutorial/Array
http://arduino-info.wikispaces.com/ArduinoPower

Basic rundown of what's happening:
Python script looks for the number of players, randomly chooses one, and sends that number to the Arduino serial port. Arduino then opens up the relays for all pins except received number.

I'm not a good coder...feel free to send suggestions to diytryin@revision3.com
