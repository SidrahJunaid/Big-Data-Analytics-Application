import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Naga on 13-03-2017.
 */
@WebServlet(name = "ImageService", urlPatterns = "/ImageService")
public class ImageService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String response="";
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(data);
        String output = "";
        JSONObject params = new JSONObject(data);
        JSONObject result = params.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");
        if (parameters.get("flowers").toString().equals("flowers")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("http://pngimg.com/uploads/tulip/tulip_PNG9018.png");
            jsonArray.put("http://cabbageroses.net/wp-content/uploads/2015/09/bigstock-cabbage-rose.jpg");
            jsonArray.put("https://s-media-cache-ak0.pinimg.com/736x/c2/3e/72/c23e729f31dd8849749734ed121ef3eb.jpg");

            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "flowers are displayed");
            js.put("displayText", "flowers are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("flowers").toString().equals("roses")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://static.pexels.com/photos/15239/flower-roses-red-roses-bloom.jpg");
            jsonArray.put("https://s-media-cache-ak0.pinimg.com/736x/a1/1e/c5/a11ec5b001c89f66c021547689a7ca1e.jpg");
            jsonArray.put("https://s-media-cache-ak0.pinimg.com/736x/8d/60/03/8d6003c9941a9b9dd8cae6514a4643f2.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "roses are displayed");
            js.put("displayText", "roses are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("flowers").toString().equals("tulips")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("http://pngimg.com/uploads/tulip/tulip_PNG9020.png");
            jsonArray.put("http://assets.teleflora.com/images/customhtml/meaning-of-flowers/tulip.png");
            jsonArray.put("http://www.woodenshoe.com/media/attila-graffiti-tulip.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "tulip are displayed");
            js.put("displayText", "tulip are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("flowers").toString().equals("sunflowers")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://s-media-cache-ak0.pinimg.com/originals/4c/14/28/4c14283d33f0f8ac462c45e53dcec2e0.jpg");
            jsonArray.put("https://s-media-cache-ak0.pinimg.com/originals/3a/27/09/3a2709f4665f502c73cdad54260747a8.jpg");
            jsonArray.put("http://s103.photobucket.com/user/broncobitch/media/Sunflower.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "sunflowers are displayed");
            js.put("displayText", "sunflowers are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("null").toString().equals("clear")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(" ");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "screen is cleared");
            js.put("displayText", "screen is cleared");
            js.put("source", "image database");
            response = js.toString();
        }
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(response);
    }
}
