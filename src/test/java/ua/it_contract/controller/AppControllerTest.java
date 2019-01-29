package ua.it_contract.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ua.it_contract.dao.DataObjectRepository;
import ua.it_contract.entity.DataObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AppControllerTest {

    private AppController controller = mock(AppController.class);
    private DataObjectRepository repository = mock(DataObjectRepository.class);

    @Test
    public void testGetDataObjects() throws IOException {

        String url = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193/documents";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response expect = client.newCall(request).execute();

        when(controller.getDataObjects(url)).thenReturn(expect);

        Response response = controller.getDataObjects(url);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        DataObject dataObject = new DataObject();
        repository.save(dataObject);
        verify(repository, atLeastOnce()).save(dataObject);
    }
}