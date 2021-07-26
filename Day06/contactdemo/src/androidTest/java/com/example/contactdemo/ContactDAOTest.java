package com.example.contactdemo;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ContactDAOTest {
    @Test
    public void testInsertContact() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Contact contact = ContactDAO.getInstance(appContext).insertContact("TKM", "18888888888");
        assertEquals("TKM", contact.getName());
        assertEquals("18888888888", contact.getNumber());
    }
}
