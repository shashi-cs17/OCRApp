package newpackage.vab.beta;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.*;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static  int REQ=1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ&&data!=null&&data.getData()!=null&&resultCode==RESULT_OK)
        {
             TextView txt= (TextView)findViewById(R.id.text);

             Bitmap bitmap = null;
            AssetManager asm= getAssets();
            TessBaseAPI tbs= new TessBaseAPI();



 //this line is currently causing sudden crash.  
            tbs.init( Environment.getExternalStorageDirectory().toString() + "/TesseractSample/","eng");
//comment it out to make the app run.
            
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                ImageView img= (ImageView)findViewById(R.id.image);
                tbs.setImage(bitmap);
                txt.setText(tbs.getUTF8Text());
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("Msg","exception");
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button =(Button)findViewById(R.id.select);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast tst= Toast.makeText(getApplicationContext(),"!",Toast.LENGTH_SHORT);
                tst.show();
                //minor toast to check if the button is functioning

                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent,REQ);

            }
        });
    }
}
