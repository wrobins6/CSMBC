package fsu.csc3560.wr.csmbc;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MorseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Inflate the layout for the fragment */

        View view = inflater.inflate(R.layout.fragment_morse, container, false);

        /* View object references */

        EditText inputText = (EditText) view.findViewById(R.id.morseInputEditTextID);
        TextView outputText = (TextView) view.findViewById(R.id.morseOutputTextViewID);
        Button convertButton = (Button) view.findViewById(R.id.morseConvertButtonID);
        Button outputButton = (Button) view.findViewById(R.id.morseOutputButtonID);

        /* Handle conversion */

        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String inputTextContent = inputText.getText().toString();
                String outputTextContent = "";

                char[] letter = {'a', 'b', 'c', 'd', 'e', 'f',
                        'g', 'h', 'i', 'j', 'k', 'l',
                        'm', 'n', 'o', 'p', 'q', 'r',
                        's', 't', 'u', 'v', 'w', 'x',
                        'y', 'z', '1', '2', '3',
                        '4', '5', '6', '7', '8', '9', '0', ' '};

                String[] code
                        = {".-", "-...", "-.-.", "-..", ".",
                        "..-.", "--.", "....", "..", ".---",
                        "-.-", ".-..", "--", "-.", "---",
                        ".--.", "--.-", ".-.", "...", "-",
                        "..-", "...-", ".--", "-..-", "-.--",
                        "--..", ".----", "..---", "...--", "....-",
                        ".....", "-....", "--...", "---..", "----.", "-----", "/"};

                /* Do not convert when input EditText is empty */

                if (inputTextContent.equals("")) {
                    Toast.makeText(getContext(), "Nothing to convert!", Toast.LENGTH_SHORT).show();
                }
                else {

                /*
                 Handle converting input text to Morse code
                 Logic and code mostly from:
                 https://www.geeksforgeeks.org/java-program-to-convert-english-text-to-morse-code-and-vice-versa/
                */

                    for (int i = 0; i < inputTextContent.length(); i++) {
                        for (int j = 0; j < letter.length; j++) {
                            if (inputTextContent.charAt(i) == letter[j]) {
                                outputTextContent += code[j] + " ";
                                break;
                            }
                        }
                    }

                    /* Set the result to the output TextView */

                    outputText.setText(outputTextContent);

                }

            }

        });

        /* Handle output */

        outputButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Do not output when output TextView is empty */

                String outputTextContent = outputText.getText().toString();
                if (outputTextContent.equals("")) {
                    Toast.makeText(getContext(), "No data to output!", Toast.LENGTH_SHORT).show();

                }
                else {

                    /* Create OutputFragment */

                    OutputFragment outputFragment = new OutputFragment();

                    /* Send the conversion result via the arguments */

                    Bundle args = new Bundle();
                    args.putString("outputData", outputTextContent);
                    outputFragment.setArguments(args);

                    /* Show OutputFragment on the screen */

                    outputFragment.show(getFragmentManager(), "OutputFragmentTAG");

                }
            }
        });

        return view;
    }

}