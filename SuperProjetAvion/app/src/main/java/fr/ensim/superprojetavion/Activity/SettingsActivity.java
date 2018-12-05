package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.ensim.superprojetavion.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.setTitle(getString(R.string.settingsName));

        //parameter
        final Switch setting1 = findViewById(R.id.switchCode);

        //set parameter value from parameter file
        try {
            FileInputStream fis = openFileInput("settings.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            setting1.setChecked((boolean) ois.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            setting1.setChecked(false);
        } catch (IOException e) {
            e.printStackTrace();
            setting1.setChecked(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            setting1.setChecked(false);
        }

        //button to save parameter value in file
        Button save = findViewById(R.id.saveAndHome);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream outputStream;
                ObjectOutputStream oos;
                try {
                    outputStream = openFileOutput("settings.txt", Context.MODE_PRIVATE);
                    oos = new ObjectOutputStream(outputStream);
                    oos.writeObject(setting1.isChecked());

                    oos.flush();
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
}
