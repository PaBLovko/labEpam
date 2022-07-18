package com.epam.esm.util;

import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.UserRole;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;



import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonConverterTest {
    @InjectMocks
    private JsonConverter jsonConverter;

    @BeforeAll
    public void init() {
        jsonConverter = new JsonConverter(new ObjectMapper());
    }

    @Test
    void convertValidTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        String expected = "{\"id\":2,\"firstName\":\"Pavel\",\"lastName\":\"Kazhamiakin\",\"email\":\"pavel@gmail" +
                ".com\",\"password\":\"$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W\"," +
                "\"role\":\"USER\",\"active\":true,\"links\":[]}";
        String actual = jsonConverter.convert(user);
        assertEquals(expected, actual);
    }

    @Test
    void convertValidTest2() {
        User user = new User(2, "Antot", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        String expected = "{\"id\":2,\"firstName\":\"Antot\",\"lastName\":\"Kazhamiakin\",\"email\":\"pavel@gmail" +
                ".com\",\"password\":\"$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W\"," +
                "\"role\":\"USER\",\"active\":true,\"links\":[]}";
        String actual = jsonConverter.convert(user);
        assertEquals(expected, actual);
    }
}
