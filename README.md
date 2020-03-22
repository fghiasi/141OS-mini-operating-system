
#See simulation at https://www.youtube.com/watch?v=SRPuDap8gTA

#141OS mini-operating system
Homework 9 for UCI Winter 2020 CS141/INF101 Programming Languages. This homework demonstrates a simulation of a mini operating system called '141OS'. 141OS allows multiple users to save and print files. The 141OS manages multiple disks, multiple printers, and multiple users – all of which can be considered concurrent processes. The goal of the system is to exploit possible parallelism to keep the devices (disks and printers) as busy as possible. The GUI is built with JavaFX and core of the code is written in Java. Multithreading is utilized to manage concurrent use of resources.


#Requirements

#Design Sketch: Part One

You will be defining the classes that work as the backend of the 141OS. For this assignment, here are some important requirements regarding the operating system:
-Files can be stored on any disk and printed on any printer by any user.
-Only one line of a file may be stored in a sector, so a file will require one sector per line.
-Although Printers and Disks can be thought of as parallel processes, I suggest you not make them Threads. If you do, you must make them Daemon threads by calling setDaemon or your program will never terminate.  
-The classes with the name “Thread” in them, e.g., UserThread & PrintJobThread, must be running on their own Thread.
-Use the StringBuffer, which is implemented as an array of char. StringBuffer is the standard unit stored on disks, sent to printers one line at a time, and handled by users one line at a time. We are using StringBuffers because Strings are not modifiable. Note also that StringBuffer.equals must do a deep equality if you plan to do comparisons.
-You can only have one user writing to a disk at a time or the files will be jumbled. You can have any number of Threads reading at a time – even when someone is writing to the disk. This is not normally the case in a real OS; I had to simplify the problem here.

Your program must be runnable from command line and take command line arguments to define the number of users, disks, and printers. You should expect to follow this format:

$ java -jar 141OS.jar -#ofUsers <Users> -#ofDisks -#ofPrinters

An example input would be

$ java -jar 141OS.jar -3 USER1 USER2 USER3 -2 -3

This specifies that 3 users, 2 Disks, and 3 Printers are defined, with User1, User2, and User3 being files that mimic user input. Store instances of the appropriate objects in three separate arrays: Users, Printers, and Disks. Refer to this for how to pass arguments to your program.

A sample of the input (USER*) and output (PRINTER*) is in the hw8 directory located in my Homework 8 directory here. The PRINTER* files will be generated by your program, but the exact files that get sent to each printer may vary.

#Design Sketch: Part Two

Build by hand-coding (i.e., don’t use a program to generate the GUI program) a GUI that displays the status of the 141OS. I want to see the following whenever the state of your program changes:

-what each User is doing
-what each Disk is doing
-what each Printer is doing

The details of the GUI are left up to you. You may use Swing, AWT, or JavaFX to develop the GUI to your liking. Add speed control so the user can speed up or slow down the simulation!

For the visual GUI, you will be submitting a URL to a video on YouTube. Here are the following requirements for the video:
-Use the sample inputs given in the homework as the User inputs.
-You will need to make 2 Disks and 3 Printers (you can assume it will only be these values)
-You need to have your Name and your UCINetID visible in the GUI itself, either before the simulation or during the simulation.
-Take a screen recording of your GUI in action with these inputs in mind. This simulation will take around 3 minutes approximately with the normal speed. Keep it at normal speed, but your speed control should be visible.
-Upload that screen recording onto YouTube, and submit the URL of that link onto the Canvas submission. You only need that link for the Canvas submission.

We will be looking for these things:
-Printers
-Disks
-Users
-Speed Control
-Concurrency – this is important.
