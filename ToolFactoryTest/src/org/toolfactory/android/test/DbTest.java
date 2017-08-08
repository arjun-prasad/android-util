package org.toolfactory.android.test;

import org.junit.Test;
import org.toolfactory.android.db.Constraint;
import org.toolfactory.android.db.SQLiteManager;
import org.toolfactory.android.db.TableGenerator;

import android.test.InstrumentationTestCase;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DbTest extends InstrumentationTestCase{   

    @Test
    public void testTableGenerator(){
        try {
            String q=TableGenerator.use(null)
                .name("CONTACT")
                    .fields(new String[]{"Name", "Contact"})
                    .types(new String[]{TableGenerator.FIELD_TYPE_TEXT, TableGenerator.FIELD_TYPE_TEXT})
                    .addConstraint(Constraint.onField("Name").
                            type(Constraint.CONSTRAINT_TYPE_PRIMARY).
                            create())
                    .addConstraint(Constraint.onField("Name").
                            type(Constraint.CONSTRAINT_TYPE_NOT_NULL).
                            create())
                    .addConstraint(Constraint.onField("Contact").
                            type(Constraint.CONSTRAINT_TYPE_FOREIGN).
                            references("M").toField("cont").
                            create())
                    .create().getQuery();
            System.out.println(q);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDatabase(){
        try {
            SQLiteManager.getSingleton(getInstrumentation().getContext(),"hello",1,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}