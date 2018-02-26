package blitzboba.blitzboba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/14/2017.
 */

public class ContactUsDialogFragment extends DialogFragment {

    public final static String FORM_DIALOG_FRAGMENT_TAG = "FORM_DIALOG_FRAGMENT_TAG";
    public static final String FONT_NAME = "fonts/ARCADE_N.TTF";
    TextView contactUsTitle;
    TextView nameTextView;
    TextView emailTextView;
    TextView messageTextView;
    EditText nameEditText;
    EditText emailEditText;
    EditText messageEditText;
    Button submitButton;
    Button cancelButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_dialog_frag, container, false);
        contactUsTitle = (TextView) view.findViewById(R.id.contact_us_title);
        nameTextView = (TextView) view.findViewById(R.id.name_title);
        nameEditText = (EditText) view.findViewById(R.id.name_editText);
        emailTextView = (TextView) view.findViewById(R.id.email_title);
        emailEditText = (EditText) view.findViewById(R.id.email_editText);
        messageTextView = (TextView) view.findViewById(R.id.message_title);
        messageEditText = (EditText) view.findViewById(R.id.message_editText);
        submitButton = (Button) view.findViewById(R.id.submit_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        submitButton.setEnabled(false);
        submitButton.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        cancelButton.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        contactUsTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        nameTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        emailTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        messageTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        nameEditText.addTextChangedListener(textWatcher);
        emailEditText.addTextChangedListener(textWatcher);
        messageEditText.addTextChangedListener(textWatcher);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailValid(emailEditText.getText().toString())) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sup@blitzboba.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Contact Us Form - Review");
                    i.putExtra(Intent.EXTRA_TEXT, messageEditText.getText() + "\n\n - " + nameEditText.getText());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(getDialog()!= null) {
            getDialog().setCanceledOnTouchOutside(false);
        }


        return view;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(nameEditText.getText().toString().isEmpty() || emailEditText.getText().toString().isEmpty() || messageEditText.getText().toString().isEmpty()) {
                submitButton.setEnabled(false);
            } else {
                submitButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
