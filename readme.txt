#This is an instruction in how to run the program.

The program is made in Kotlin as an Android mobile application. 

SETUP METHOD ONE (Installing app without Android Studio IDE, Kotlin SDK's etc.): 
1. Use the handed-in .apk file to install the application on an Android device. 

SETUP METHOD TWO: 
1. Open Android Studio IDE.
2. Import project 
3. Set SDK as Android API 30
4. Build project
5a. Setup an Android emulator within the IDE such as Pixel 4, Pixel 3 or similar. 
- OR - 
5b. Connect a physical Android device to your pc using a cable and allow phyiscal device debug mode within the IDE. 
6. Click the run icon and the application starts on the setup device.


HOW TO PLAY?
1. Select the category which you want to play
2. Click on the piece you want to move, and then click on the field you want it moved to.
3. If you are playing the AI opponent, then the AI opponent will calculate its next move for max 15 seconds, and then make its move. 
4. Repeat steps 2-3 until stale mate or a winner has been found.

KNOWN ISSUES:
the AI takes so long that the app will freeze up. you can spam the wait button popup to eventuallly see a move (not ideal). can be fixed by implementing a coroutine for long calculations with the min-max algorithm
