package edu.lwtech.csd297.topten;

import java.util.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import edu.lwtech.csd297.topten.pojos.*;

class TopTenListTests {

    TopTenList romanList;
    TopTenList planetsList;
    TopTenList starWarsList;

    @BeforeEach
    void setUp() {
        String description;
        List<String> items;
        int owner = 1;

        description = "Top 10 Favorite Roman Numerals";
        items = Arrays.asList(
            "X",
            "IX",
            "VIII",
            "VII",
            "VI",
            "V",
            "IV",
            "III",
            "II",
            "I"
        );
        romanList = new TopTenList(description, items, owner);

        description = "Top 10 Favorite Planets";
        items = Arrays.asList(
            "Hollywood",
            "Neptune",
            "Uranus",
            "Venus",
            "Mercury",
            "Earth",
            "Mars",
            "Jupiter",
            "Saturn",
            "Pluto!!!"
        );
        planetsList = new TopTenList(description, items, owner);

        description = "Top 10 Favorite Star Wars Movies";
        items = Arrays.asList(
            "III: Revenge of the Sith",
            "II: Attack of the Clones",
            "VIII: The Last Jedi",
            "IX: The Rise of Skywalker",
            "VI: Return of the Jedi",
            "I: The Phantom Menace",
            "VII: The Force Awakens",
            "The Mandalorian Compilation",
            "IV: A New Hope",
            "V: The Empire Strikes Back"
        );
        starWarsList = new TopTenList(description, items, owner);
    }

    @Test
    void testConstructor() {
        Exception ex = null;
        
        List<String> emptyList = new ArrayList<>();
        List<String> fullList = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            fullList.add("Item " + i);
        }

        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(-666, "description", fullList, false, 1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("recID"));

        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, null, fullList, false, 1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("description is null"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "", fullList, false, 1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("description is empty"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "description", null, false, 1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("item list is null"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "description", emptyList, false, 1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("less than 10 items"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "description", fullList, false, -1, 2, 3); }
        );
        assertTrue(ex.getMessage().contains("ownerID"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "description", fullList, false, 1, -2, 3); }
        );
        assertTrue(ex.getMessage().contains("numViews"));
        
        ex = assertThrows(IllegalArgumentException.class,
            () -> { new TopTenList(123, "description", fullList, false, 1, 2, -3); }
        );
        assertTrue(ex.getMessage().contains("numLikes"));
    }

    @Test
    void testGetId() {
        assertEquals(-1, romanList.getRecID());
        assertEquals(-1, planetsList.getRecID());
        assertEquals(-1, starWarsList.getRecID());
    }

    @Test
    void testSetRecID() {
        // Add appropriate assert calls here
    }

    @Test
    void testGetDescription() {
        // Add appropriate assert calls here
    }

    @Test
    void getItems() {
        List<String> items = null;
        items = Arrays.asList(
            "X",
            "IX",
            "VIII",
            "VII",
            "VI",
            "V",
            "IV",
            "III",
            "II",
            "I"
        );
        // Add appropriate assert calls here
    }

    @Test
    void testGetOwnerID() {
        // Add appropriate assert calls here
    }

    @Test
    void testPublish() {
        // Add appropriate assert calls here
    }

    @Test
    void testGetNumViews() {
        // Add appropriate assert calls here
    }

    @Test
    void testGetNumLikes() {
        // Add appropriate assert calls here
    }
    
    @Test
    void testToString() {
        // Add appropriate assert calls here
    }

}
