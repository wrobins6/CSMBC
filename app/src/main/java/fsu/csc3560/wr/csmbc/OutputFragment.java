package fsu.csc3560.wr.csmbc;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class OutputFragment extends DialogFragment {

    public static final String TAG = "OutputFragmentTAG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Inflate the layout for the DialogFragment */

        View view = inflater.inflate(R.layout.fragment_output, container, false);

        /* Get arguments and resources to use later */

        Bundle args = getArguments();
        Resources res = getResources();

        /* View object references */

        Button confirmButton = (Button) view.findViewById(R.id.outputConfirmButtonID);
        Button cancelButton = (Button) view.findViewById(R.id.outputCancelButtonID);
        Spinner outputModeSpinner = (Spinner) view.findViewById(R.id.outputModeSpinnerID);

        /* Get the converted data from arguments */

        String outputTextContent = args.getString("outputData");
        String outputType;
        String[] outputArray;
        boolean hasFlash = false;
        boolean hasVibrator = false;

        /* Determine the type of data to be output, and set the appropriate items for the spinner */

        if (outputTextContent.contains("0") || outputTextContent.contains("1")) {
            outputType = "Binary";
            outputArray = res.getStringArray(R.array.output_array1);
        }
        else {
            outputArray = res.getStringArray(R.array.output_array2);
            outputType = "Morse";
        }

        /* Create customized spinner using ArrayAdapter with the previously set items, and pre-defined layouts in the Android SDK */

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, outputArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        outputModeSpinner.setAdapter(arrayAdapter);

        /* Dismiss the DialogFragment when the Cancel button is clicked */

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        /*
        Check if the device has a camera and flash
        Logic from:
        https://youtu.be/S_WnT5ynV5g
        */

        if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                hasFlash = true;
            }
        }

        /* Check if the device has a vibrator */

        Vibrator testVibrate = (Vibrator) getActivity().getSystemService(MainActivity.VIBRATOR_SERVICE);

        if (testVibrate.hasVibrator()) {
            hasVibrator = true;
        }

        /* If the device has a flash and a vibrator, enable the confirm Button view */

        if (hasFlash && hasVibrator) {
            confirmButton.setEnabled(true);
        }

        /* Output the data when the Confirm button is clicked */

        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Get the selected outputMode */

                String outputSelect = outputModeSpinner.getSelectedItem().toString();

                /*
                 CameraManager and Vibrator object references to use the Torch and Vibrator
                 See the Android documentation for more details:
                 https://developer.android.com/reference/android/hardware/camera2/CameraManager.TorchCallback
                 https://developer.android.com/reference/android/os/Vibrator
                 */

                CameraManager cameraManager = (CameraManager) getActivity().getSystemService(MainActivity.CAMERA_SERVICE);
                Vibrator vibrate = (Vibrator) getActivity().getSystemService(MainActivity.VIBRATOR_SERVICE);

                /*
                 Handle hardware output in its own thread
                 This fixes issues where the application would hang when trying to interact with the UI during output
                */

                OutputRunnable outputRunnable = new OutputRunnable(outputTextContent, outputType, outputSelect, cameraManager, vibrate);
                new Thread(outputRunnable).start();
            }
        });

        return view;
    }
}