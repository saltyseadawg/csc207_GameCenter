package a207project.fall18.gamecenter;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CustomAdapterTest {

    /**
     * An instance of CustomAdapter
     */
    private CustomAdapter customAdapter;
    @Before
    public void setUp() {
        ArrayList<Button> arrayButtons = new ArrayList<>();
        GameActivity gameActivity = mock(GameActivity.class);
        arrayButtons.add(new Button(gameActivity));
        customAdapter = new CustomAdapter(arrayButtons, 10, 10);
    }

    /**
     * Test whether getCount works
     */
    @Test
    public void getCount() {
        assertEquals(1, customAdapter.getCount());
    }

    /**
     * Test whether getItem works
     */
    @Test
    public void getItem() {
        assertNotNull(customAdapter.getItem(0));
    }

    /**
     * Test getItemId works
     */
    @Test
    public void getItemId() {
        assertEquals(0, customAdapter.getItemId(0));
    }
}