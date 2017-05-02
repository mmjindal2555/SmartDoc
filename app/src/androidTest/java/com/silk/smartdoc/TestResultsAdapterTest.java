package com.silk.smartdoc;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silk.smartdoc.Model.CentreAndPrice;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.View.MedicineResultsAdapter;
import com.silk.smartdoc.View.TestsResultsAdapter;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by dodobhoot on 5/2/2017.
 */

public class TestResultsAdapterTest extends AndroidTestCase {
    public TestsResultsAdapter mAdapter;

    private DiagnosticCenter d1;
    private DiagnosticCenter d2;

    public TestResultsAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<DiagnosticCenter> diagnos = new ArrayList<DiagnosticCenter>();
        ArrayList<CentreAndPrice> cenAndpri = new ArrayList<CentreAndPrice>();

        d1 = new DiagnosticCenter("Thyrocare Laboratories", "VIP Road, Baguihti, Jora Mandir, Kolkata, West Bengal","NABL, ISO", "www.thyrocare.com","-Kj6_1IEZcDmGN1O6zln");
        d2 = new DiagnosticCenter("Pathcare Laboratories Ltd", "Kolkata", "NABL, CAP, ISO","www.pathcarelabs.com","-Kj6_1IEZcDmGN1O6zln");
        diagnos.add(d1);
        diagnos.add(d2);

        CentreAndPrice c1 = new CentreAndPrice("-Kj6YRd-0dUFx6_Legx",850);
        CentreAndPrice c2 = new CentreAndPrice("-Kj6_1IEZcDmGN1O6zln",525);
        cenAndpri.add(c1);
        cenAndpri.add(c2);
        mAdapter = new TestsResultsAdapter(diagnos, cenAndpri, getContext());
    }


    public void testGetMedName() {
        assertEquals("Thyrocare Laboratories was expected.", d1.getName(),
                ((DiagnosticCenter) mAdapter.getItem(0)).getName());
    }

    // Testing 3 views on my adapter
    public void testGetView() {
        View view = mAdapter.getView(0, null, null);

        TextView centerName = (TextView) view.findViewById(R.id.centreNameTV);
        TextView price = (TextView) view.findViewById(R.id.testPrice);
        TextView certifications = (TextView) view.findViewById(R.id.certificatesTextView);

        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", view);
        assertNotNull("Name TextView is null. ", centerName);
        assertNotNull("Price TextView is null. ", price);
        assertNotNull("Certifications is null. ", certifications);

        assertEquals("Names doesn't match.", d1.getName(), centerName.getText());
    }
}