package fsu.csc3560.wr.csmbc;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Inflate the layout for the fragment */

        View view = inflater.inflate(R.layout.fragment_base, container, false);

        /* View object references */

        Spinner inputSpinner = (Spinner) view.findViewById(R.id.baseInputSpinnerID);
        Spinner outputSpinner = (Spinner) view.findViewById(R.id.baseOutputSpinnerID);
        EditText inputText = (EditText) view.findViewById(R.id.baseInputEditTextID);
        TextView outputText = (TextView) view.findViewById(R.id.baseOutputTextViewID);
        Button convertButton = (Button) view.findViewById(R.id.baseConvertButtonID);
        Button outputButton = (Button) view.findViewById(R.id.baseOutputButtonID);

        /* Handle input spinner options */

        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        inputText.setText("");
                        outputText.setText("");

                        /* Limit the length of the binary input to 8 bits, and use the numeric keyboard */

                        inputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                        inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 1:
                        inputText.setText("");
                        outputText.setText("");

                        /* Limit the length of the hexadecimal input to 2 digits, and use the standard keyboard */

                        inputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 2:
                        inputText.setText("");
                        outputText.setText("");

                        /* Limit the length of the decimal input to 4 characters, and use the numeric keyboard */

                        inputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                        inputText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        /* Handle output Spinner options */

        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                outputText.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        /* Handle conversion */

        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Get current Strings from input Spinner, output Spinner, and input EditText */

                String inputSelect = inputSpinner.getSelectedItem().toString();
                String outputSelect = outputSpinner.getSelectedItem().toString();
                String inputTextContent = inputText.getText().toString();
                String outputTextContentNum;
                Integer inputTextContentNum;
                byte outputByte = 0x00;

                /* Do not convert when input EditText is empty */

                if (inputTextContent.equals("")) {
                    Toast.makeText(getContext(), "Nothing to convert!", Toast.LENGTH_SHORT).show();
                }

                else {
                    switch (inputSelect) {
                        case "Hexadecimal":

                            /*
                            Make sure the input does not contain "+" or "-",
                            since they are technically valid, but we do not want them.
                            */

                            if (inputTextContent.contains("+") || inputTextContent.contains("-")) {
                                Toast.makeText(getContext(), "Not a valid hexadecimal number!", Toast.LENGTH_SHORT).show();
                                outputText.setText("");
                            }
                            else {
                                try {

                                    /* Parse the hexadecimal value from the input */

                                    inputTextContentNum = Integer.parseInt(inputTextContent, 16);

                                    /* Get the byte value of the hexadecimal input */

                                    outputByte = inputTextContentNum.byteValue();
                                    switch (outputSelect) {
                                        case "Hexadecimal":

                                            /*
                                            Pad with a leading 0 if necessary,
                                            then set result to the output TextView,
                                            */

                                            outputTextContentNum = String.format("%2s", inputTextContent).replace(' ', '0');
                                            outputText.setText(outputTextContentNum);
                                            break;
                                        case "Binary":

                                            /*
                                            Convert hexadecimal to binary while masking leading 1s for the sign,
                                            pad with leading 0s if necessary, then set the result to the output TextView
                                            */

                                            outputTextContentNum = Integer.toBinaryString(outputByte & 0xFF);
                                            outputTextContentNum = String.format("%8s", outputTextContentNum).replace(' ', '0');
                                            outputText.setText(outputTextContentNum);
                                            break;
                                        case "Decimal":

                                            /*
                                            Get the decimal value of the input,
                                            then set the result to the output TextView
                                            */

                                            outputTextContentNum = String.valueOf(outputByte);
                                            outputText.setText(outputTextContentNum);
                                            break;
                                    }
                                }

                                /* In the event we get an invalid hexadecimal input */

                                catch (Exception e) {
                                    Toast.makeText(getContext(), "Not a valid hexadecimal number!", Toast.LENGTH_SHORT).show();
                                    outputText.setText("");
                                }
                            }
                            break;
                        case "Binary":
                            try {

                                /* Parse the binary value from the input */

                                inputTextContentNum = Integer.parseInt(inputTextContent, 2);

                                /* Get the byte value of the binary input */

                                outputByte = inputTextContentNum.byteValue();
                                switch (outputSelect) {
                                    case "Hexadecimal":

                                        /*
                                        Convert binary to hexadecimal while masking leading 1s for the sign,
                                        pad with a leading 0 if necessary, then set the result to the output TextView
                                        */

                                        outputTextContentNum = Integer.toHexString(outputByte & 0xFF);
                                        outputTextContentNum = String.format("%2s", outputTextContentNum).replace(' ', '0');
                                        outputText.setText(outputTextContentNum);
                                        break;
                                    case "Binary":

                                        /*
                                        Pad with leading 0s if necessary,
                                        then set result to the output TextView
                                        */

                                        outputTextContentNum = String.format("%8s", inputTextContent).replace(' ', '0');
                                        outputText.setText(outputTextContentNum);
                                        break;
                                    case "Decimal":

                                        /*
                                        Get the decimal value of the input,
                                        then set the result to the output TextView
                                        */

                                        outputTextContentNum = String.valueOf(outputByte);
                                        outputText.setText(outputTextContentNum);
                                        break;
                                }
                            }

                            /* In the event we get an invalid binary input */

                            catch (Exception e) {
                                Toast.makeText(getContext(), "Not a valid binary number!", Toast.LENGTH_SHORT).show();
                                outputText.setText("");
                            }
                            break;
                        case "Decimal":
                            try {

                                /* Parse the decimal value from the input */

                                inputTextContentNum = Integer.parseInt(inputTextContent, 10);

                                /* Make sure the decimal input is within the 8-bit two's complement range */

                                if (inputTextContentNum > 127 || inputTextContentNum < -128) {
                                    Toast.makeText(getContext(), "Not in range [-128,128)!", Toast.LENGTH_SHORT).show();
                                    outputText.setText("");
                                }
                                else {

                                    /* Get the byte value of the decimal input */

                                    outputByte = inputTextContentNum.byteValue();
                                    switch (outputSelect) {
                                        case "Hexadecimal":

                                            /*
                                            Convert decimal to hexadecimal while masking leading 1s for the sign,
                                            pad with a leading 0 if necessary, then set the result to the output TextView
                                            */

                                            outputTextContentNum = Integer.toHexString(outputByte & 0xFF);
                                            outputTextContentNum = String.format("%2s", outputTextContentNum).replace(' ', '0');
                                            outputText.setText(outputTextContentNum);
                                            break;
                                        case "Binary":

                                            /*
                                            Convert decimal to binary while masking leading 1s for the sign,
                                            pad with leading 0s if necessary, then set the result to the output TextView
                                            */

                                            outputTextContentNum = Integer.toBinaryString(outputByte & 0xFF);
                                            outputTextContentNum = String.format("%8s", outputTextContentNum).replace(' ', '0');
                                            outputText.setText(outputTextContentNum);
                                            break;

                                        case "Decimal":

                                            /*
                                            Get the decimal value of the input,
                                            then set the result to the output TextView
                                            This is done to eliminate malformed decimal inputs
                                            */

                                            outputTextContentNum = String.valueOf(outputByte);
                                            outputText.setText(outputTextContentNum);
                                            break;
                                    }

                                }
                            }

                            /* In the event we get an invalid decimal input */

                            catch (Exception e) {
                                Toast.makeText(getContext(), "Not a valid decimal number!", Toast.LENGTH_SHORT).show();
                                outputText.setText("");
                            }
                            break;

                    }
                }

            }
        });

        /* Handle output */

        outputButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String outputSelect = outputSpinner.getSelectedItem().toString();
                String outputTextContent = outputText.getText().toString();

                /* Do not output when output TextView is empty */

                if (outputTextContent.equals("")) {
                    Toast.makeText(getContext(), "No data to output!", Toast.LENGTH_SHORT).show();

                }

                /* Only output when the result is binary */

                else if (outputSelect.equals("Binary")) {

                    /* Create OutputFragment */

                    OutputFragment outputFragment = new OutputFragment();

                    /* Send the conversion result via the arguments */

                    Bundle args = new Bundle();
                    args.putString("outputData", outputTextContent);
                    outputFragment.setArguments(args);

                    /* Show OutputFragment on the screen */

                    outputFragment.show(getFragmentManager(), "OutputFragmentTAG");

                }

                /* Handle results of other bases */

                else {
                    Toast.makeText(getContext(), "Result is not binary!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}