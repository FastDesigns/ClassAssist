package com.example.danielle.classassist;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewMessage
{
    private String message;
    private Context context;

    public NewMessage(String msg, Context c)
    {
        message = msg;
        context = c;
        dialog(R.layout.content_message, message);
    }

    //Creates an error message
    private void dialog(int layout, String m)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.setTitle("Error");
        Button btnDismiss = (Button) dialog.findViewById(R.id.messagedismiss);
        TextView text = (TextView)dialog.findViewById(R.id.message);
        text.setText(m);

        btnDismiss.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                dialog.cancel();
            }
        });;

        dialog.show();
    }
}
