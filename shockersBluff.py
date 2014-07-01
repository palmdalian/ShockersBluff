import sys, os, random, time
players = raw_input("Enter Number of Players: ") #raw_input for Python version 2.6/7?
var = 1
while var == 1: #always true / make it loop forever
	zap = random.randint(1, int(players)) #choose random player

	raw_input("Ready?") # Wait for enter before continuing
	print 'Preparing to Zap'
	time.sleep(random.randint(2, 5)) # random time between 2-5 seconds

# write chosen number directly to arduino serial port..replace with your arduino's port. Serial monitor must be open!
	tty = os.open('/dev/tty.usbmodem1421', os.O_WRONLY | os.O_NOCTTY) 
	os.write(tty, str(zap))
	raw_input("Press enter to find the faker...")
	print('Player ' +  str(zap) + ' was not zapped!')
