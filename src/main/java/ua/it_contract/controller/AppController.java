package ua.it_contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.it_contract.dao.DataObjectRepository;
import ua.it_contract.entity.DataObject;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class AppController {

    private DataObjectRepository repository;

    @Autowired
    public AppController(DataObjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public String viewIndex() {
        return "index";
    }

    @GetMapping("/link/{id}")
    public String viewLink(@PathVariable Integer id) throws IOException {
        String url = "";
        switch (id) {
            case 1:
                url = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193/documents";
                break;
            case 2:
                url = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/4805f381d48948b1b34d6ea2daa029a3/documents";
                break;
            case 3:
                url = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/47fa8764e1b74f4db58f84c9db460566/documents";
        }
        getDataObjects(url);
        return "redirect:/data";
    }

    Response getDataObjects(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

        DataObject[] list;
        if (response.body() != null) {
            JsonNode node = objectMapper.readTree(response.body().string());
            list = objectMapper.treeToValue(node.get("data"), DataObject[].class);
            Arrays.stream(list).forEach(o -> repository.save(o));
        }
        return response;
    }

    // было написано для верификации данных чтобы не лазить в консоль БД
    @GetMapping("data")
    public String viewData(Model model) {
        model.addAttribute("datas", repository.findAll());
        return "data";
    }
}
