# Desktop Bot
A simple program that mimics user actions on the OS level.

This is a task I wanted to do in my spare time to create a simple improvised implementation of a desktop bot. I used raw Java with a simple Swing GUI in addition to a library to provide global keyboard and mouse listeners - JNativeHook library https://github.com/kwhat/jnativehook.

Upon launching, the program starts a GUI which is used by the user to record movements - mouse click, key press, mouse pointer movements etc. After recording, the set of movements is saved wtih a specific time at which it will be executed. The program executes it when it is time automatically (using local time to know when).

Additionally, the user has an option to save a specific set of movements on a file and load it later from a file chooser. Could be used for presentations that requires OS level keyboard/mouse events.


