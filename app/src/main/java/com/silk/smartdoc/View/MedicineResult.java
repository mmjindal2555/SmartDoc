package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import com.silk.smartdoc.R;

import java.util.ArrayList;
class MessageDetails {

    String MName;
    String MChemicalName;
    String MPrice;

    public String getMName() {
        return MName;
    }

    public void setMName(String name) {
        this.MName = name;
    }

    public String getMChemicalName() {
        return MChemicalName;
    }

    public void setMChemicalName(String sub) {
        this.MChemicalName = sub;
    }

    public String getMPrice() {
        return MPrice;
    }

    public void setMPrice(String price) {
        this.MPrice = price ;
    }
}
public class MedicineResult extends AppCompatActivity {

    ListView msgList;
    ArrayList<MessageDetails> details;
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_result);
        msgList = (ListView) findViewById(R.id.MedicineDetails);
        details = new ArrayList<MessageDetails>();



        MessageDetails Detail;
        Detail = new MessageDetails();
        Detail.setMName("P650");
        Detail.setMChemicalName("Peracetamol");
        Detail.setMPrice("Rs 5.00");
        details.add(Detail);


        Detail = new MessageDetails();
        Detail.setMName("Itrasys");
        Detail.setMChemicalName("Itraconozole");
        Detail.setMPrice("Rs 12.00");
        details.add(Detail);

        Detail = new MessageDetails();
        Detail.setMName("IFN");
        Detail.setMChemicalName("Terbanifine");
        Detail.setMPrice("Rs 12.00");
        details.add(Detail);


        msgList.setAdapter(new MedicineCustomAdapter( this,details));
    }
}
