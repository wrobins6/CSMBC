package fsu.csc3560.wr.csmbc;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Vibrator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OutputRunnable implements Runnable {

    /* Class attributes  */

    private final String outputTextContent;
    private final String outputType;
    private final String outputSelect;
    private final CameraManager cameraManager;
    private final Vibrator vibrate;
    private static Lock lock = new ReentrantLock();

    /* Constructor to set up Runnable */

    OutputRunnable(String outputTextContent, String outputType, String outputSelect, CameraManager cameraManager, Vibrator vibrate) {
        this.outputTextContent = outputTextContent;
        this.outputType = outputType;
        this.outputSelect = outputSelect;
        this.cameraManager = cameraManager;
        this.vibrate = vibrate;
    }

    @Override
    public void run() {

        /*
         Only allow one hardware output instance
         Logic from: https://stackoverflow.com/questions/13602325/only-one-instance-of-a-thread
        */

        if (OutputRunnable.lock.tryLock()) {

            try {

                /* When the the type is Binary */

                if (outputType.equals("Binary")) {

                    long delayShort = 1000;
                    long delayLong = 1500;
                    long lightShort = 500;
                    long vibrateShort = 500;
                    long vibrateLong = 1000;

                    switch (outputSelect) {
                        case ("Flashlight"):
                            try {
                                for (int i = 0; i < outputTextContent.length(); i++) {

                                    /* Turn on the light if we get a HIGH */

                                    if (outputTextContent.charAt(i) == '1') {
                                        cameraManager.setTorchMode("0", true);

                                        /* Keep the light on for 500 milliseconds */

                                        try {
                                            Thread.sleep(lightShort);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        cameraManager.setTorchMode("0", false);
                                    }

                                    /* Delay for 500 milliseconds for a LOW */

                                    else {
                                        try {
                                            Thread.sleep(lightShort);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    /* Delay 1 second between actions */

                                    try {
                                        Thread.sleep(delayShort);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                        case ("Vibration"):
                            for (int i = 0; i < outputTextContent.length(); i++) {

                                /* Turn on the vibrator for 500 milliseconds for a HIGH */

                                if (outputTextContent.charAt(i) == '1') {
                                    vibrate.vibrate(vibrateShort);
                                }

                                /* Turn on the vibrator for 1 second for a LOW */

                                else {
                                    vibrate.vibrate(vibrateLong);
                                }

                                /* Delay 1.5 seconds between actions */

                                try {
                                    Thread.sleep(delayLong);
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case ("Flashlight+Vibration"):
                            try {
                                for (int i = 0; i < outputTextContent.length(); i++) {

                                    /* Turn on the light if we get a HIGH */

                                    if (outputTextContent.charAt(i) == '1') {
                                        cameraManager.setTorchMode("0", true);

                                        /* Keep the light on for 500 milliseconds */

                                        try {
                                            Thread.sleep(lightShort);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        cameraManager.setTorchMode("0", false);
                                    }

                                    /* Turn on the vibrator for 500 milliseconds for a LOW */

                                    else {
                                        vibrate.vibrate(vibrateShort);
                                    }

                                    /* Delay 1 second between actions */

                                    try {
                                        Thread.sleep(delayShort);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                }

                /* When the type is Morse code */

                if (outputType.equals("Morse")) {

                    long functionDelay = 500; //Wait time between each character
                    long standardDelay = 500; //How long each space and character should be
                    long shortOutput = standardDelay;
                    long longOutput = shortOutput * 3; //Dashes are always 3 times longer than dots in morse code

                    switch (outputSelect) {
                        case ("Flashlight"):
                            try {
                                for (int i = 0; i < outputTextContent.length(); i++) {

                                    /*Turn on the light for shortOutput milliseconds if the current character is "." */

                                    if (outputTextContent.charAt(i) == '.') {
                                        cameraManager.setTorchMode("0", true);
                                        // Keep the light on for shortOutput milliseconds
                                        try {
                                            Thread.sleep(shortOutput);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        cameraManager.setTorchMode("0", false);
                                        // Wait functionDelay milliseconds between characters
                                        try {
                                            Thread.sleep(functionDelay);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    /*Turn on the light for longOutput milliseconds if the current character is "-" */

                                    if (outputTextContent.charAt(i) == '-') {
                                        cameraManager.setTorchMode("0", true);

                                        // Keep the light on for 1500 milliseconds

                                        try {
                                            Thread.sleep(longOutput);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        cameraManager.setTorchMode("0", false);
                                        // pause between characters
                                        try {
                                            Thread.sleep(functionDelay);
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    /* Wait standardDelay milliseconds between characters */

                                    try {
                                        Thread.sleep(standardDelay);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                        case ("Vibration"):
                            for (int i = 0; i < outputTextContent.length(); i++) {

                                /* Turn on the vibrator for shortOutput milliseconds if the current character is "." */

                                if (outputTextContent.charAt(i) == '.') {
                                    vibrate.vibrate(shortOutput);
                                    // Wait functionDelay milliseconds between characters
                                    try {
                                        Thread.sleep(functionDelay);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                /* Turn on the vibrator for longOutput milliseconds if the current character is "-" */

                                if (outputTextContent.charAt(i) == '-') {
                                    vibrate.vibrate(longOutput);
                                    // Wait 2000 milliseconds between characters. This feels like the right delay time for a longOutput.
                                    try {
                                        Thread.sleep(2000);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Wait functionDelay milliseconds between characters
                                else {
                                    try {
                                        Thread.sleep(functionDelay);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                    }

                }

            }
            finally {
                OutputRunnable.lock.unlock();
            }

        }

    }
}
