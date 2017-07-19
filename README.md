# simpleAutomationTool
A simple console program that mimics user actions on the OS level.

This is a simple task I wanted to do in my spare time and for practice while trying to create something similar to professional keylogger/automation software. Since using only raw Java code to accomplish this is impossible I used the JNativeHook library which can be found here: https://github.com/kwhat/jnativehook.

Upon launching, the program creates a GUI that the user uses to record his/her movements - mouse click, keyboard press, mouse pointer movements etc. After recording, the set of actions are saved in a set of recordings wtih a specific time at which it will be executed. The program checks if it is time for execution constantly (using local time).

Jar file (for demo) and source code included.

