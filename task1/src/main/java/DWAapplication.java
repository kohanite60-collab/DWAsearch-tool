import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import pojo.JoinCountry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DWAapplication {


    public static void main(String[] args) throws IOException {

        System.out.println("Hello World!");

        // 获取默认配置的 HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();



        String url="https://api.worldaquatics.com/fina/competitions/5019/athletes?gender=&countryId=";

        // 创建 GET 请求对象
        HttpGet httpGet = new HttpGet(url);
// 调用 HttpClient 的 execute 方法执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
// 获取请求状态
        int code = response.getCode();

        try {

            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();


            List<JoinCountry> joinCountry = objectMapper.readValue(result, new TypeReference<List<JoinCountry>>(){});

            for (JoinCountry joinCountry1 : joinCountry) {
                System.out.println(joinCountry1.getCountryName()+" ");
                System.out.println(joinCountry1.getCountryCode()+" ");
                List<JoinCountry.Participations> list = joinCountry1.getParticipations();
                if (list != null) {
                    for (JoinCountry.Participations p : list) {
                        System.out.println(p.getPreferredFirstName() + " " + p.getPreferredLastName());
                    }
                }
                System.out.println("\n");
            }



        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }


    }
}
