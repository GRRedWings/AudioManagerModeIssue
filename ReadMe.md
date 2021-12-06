This sample app is used to show that the AudioMode works different on different verisons of Android. 

What I see on Android 11+ is that when I enable Bluetooth SCO, and set AudioManager to mode MODE_IN_COMMUNICATION, something is switching the mode back to MODE_NORMAL. The problem with this mode changing, if I pair this with logic that needs to use the microphone, as soon as the mode changes, the microphone and the TTS no longer work on the BT headset.  

Android 10 - Using a Honeyewell CT40.  The AudioMode never changes.  It is running Android 10 Kernal Version 4.14.163-perf+
#1 Fri Aug 13 11:11:29 HKT 2021

Android 11 - I have used a Zebra TC52.  This one shows that the AudioMode changes often regardless of if TTS is speaking or not.  

Android 12 - I have used a Pixel 4.  It acts the same as the Android 11 device.