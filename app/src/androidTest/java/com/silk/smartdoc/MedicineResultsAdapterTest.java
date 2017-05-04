package com.silk.smartdoc;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.View.MedicineResultsAdapter;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by dodobhoot on 5/2/2017.
 */

public class MedicineResultsAdapterTest extends AndroidTestCase {
    public MedicineResultsAdapter mAdapter;

    private Medicine m1;
    private Medicine m2;

    public MedicineResultsAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<Medicine> data = new ArrayList<Medicine>();

        m1 = new Medicine("Paracetamol", "Aspirin",11.0, "Ranbaxy");
        m2 = new Medicine("Glucose", "Glucon D", 51, "Sun Pharma");
        data.add(m1);
        data.add(m2);
        mAdapter = new MedicineResultsAdapter(data, getContext());
    }


    public void testGetMedName() {
        assertEquals("Aspirin was expected.", m1.getName(),
                ((Medicine) mAdapter.getItem(0)).getName());
    }


    // I have 3 views on my adapter
    public void testGetView() {
        View view = mAdapter.getView(0, null, null);

        TextView medicineName = (TextView) view.findViewById(R.id.medNameTV);
        TextView price = (TextView) view.findViewById(R.id.medPrice);
        TextView manufacturer = (TextView) view.findViewById(R.id.manufacturerTextView);

        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", view);
        assertNotNull("Name TextView is null. ", medicineName);
        assertNotNull("Price TextView is null. ", price);
        assertNotNull("Manufacturer TextView is null. ", manufacturer);

        assertEquals("Names doesn't match.", m1.getName(), medicineName.getText());
    }
}