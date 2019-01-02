package live.validate.jun.service;

import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

public class Validator {
	private static String ts = null;
	private static String userid = null;
	private static String id = null;
	private static String secretkey = null;//两种验证方式secretkey不同，故有两个init版本
	private static String token = null;
	private static String sign = null;
	private static String nickname = "Ti";
	private static String url = null;
	private static String marqueeName = "test";
	private static String avatar = "http://live.polyv.net/assets/images/avatars/9avatar.jpg";
	private static int status = 1;
	
	
	
	public static String getSDCallBack(HttpServletRequest req) {
		initSD(req);
		JSONObject json = new JSONObject();
		ts = String.valueOf(System.currentTimeMillis());
		if(sign == token) {
			String tmp = secretkey + id + secretkey + ts + secretkey + userid;
			sign = DigestUtils.md5Hex(tmp.getBytes(Charset.forName("UTF-8")));
			String res = url + "?userid=" + userid + "&nickname=" + nickname + "&marqueeName=" +
			marqueeName + "&avatar=" + avatar + "&ts=" + ts + "&sign=" + sign; 
			String finalRes = "<script language='javascript' type='text/javascript'>location.href='"+res+"'</script>";
			System.out.println("finalRes:"+finalRes);
			return finalRes;
		}
		return null;
	}
	
	public static String getOVCallBack(HttpServletRequest req) {
		initOV(req);
		JSONObject json = new JSONObject();
		if(sign != token) {
			json.put("status",0);
			json.put("errorUrl", "http://www.baidu.com");
		}
		else {
			json.put("status", 1);
			json.put("userid", userid);
			json.put("nickname", nickname);
			json.put("avatar", avatar);
			System.out.println(json.toString());
			return json.toString();
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	public static void initOV(HttpServletRequest req) {
		secretkey = "em31gCSY1h";
		token = req.getParameter("token");
		ts = req.getParameter("ts");
		userid = req.getParameter("userid");
		String plain = secretkey+userid+secretkey+ts;
		System.out.println("plain:"+plain);
		sign = DigestUtils.md5Hex(plain.getBytes(Charset.forName("UTF-8")));
		System.out.println("sign:"+sign);
	}
	public static void initSD(HttpServletRequest req) {
		secretkey = "KWWXssNYBK";
		sign = req.getParameter("sign");
		ts = req.getParameter("ts");
		id = req.getParameter("id");
		url = req.getParameter("url");
		userid = "eciyhturt8";
		String plain = secretkey+id+secretkey+ts;
		token = DigestUtils.md5Hex(plain.getBytes(Charset.forName("UTF-8")));
	}
	
	public static void execute(ServletRequest request, ServletResponse response) throws Exception {
        try {
        	secretkey = "em31gCSY1h";
            String userid = request.getParameter("userid"); //保利威传递的userid
            String token = request.getParameter("token");//保利威传递的token
            System.out.println("token:"+token);
            String ts = request.getParameter("ts");
            if (userid != null && String.valueOf(userid).equals(userid)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(secretkey).append(userid).append(secretkey).append(ts);
                String signSource = stringBuilder.toString();
                System.out.println("plain:"+signSource);
                String sign = DigestUtils.md5Hex(signSource).toLowerCase();//signSource.getByte(utf8)
                System.out.println("sign:"+sign);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                if (sign.equals(token)) {
                    response.getWriter().append("{\"status\":1,\"userid\":\"" + userid + "\",\"nickname\":\"" + nickname + "\",\"avatar\":\"" + avatar + "\"}");
                    System.out.println("{\"status\":1,\"userid\":\"" + userid + "\",\"nickname\":\"" + nickname + "\",\"avatar\":\"" + avatar + "\"}");
                } else {
                    response.getWriter().append("{\"status\":0,\"errorUrl\":\"\"}");
                    System.out.println("{\"status\":0,\"errorUrl\":\"\"}");
                }

            } else {
                response.getWriter().append("{\"status\":0,\"errorUrl\":\"\"}");
                System.out.println("{\\\"status\\\":0,\\\"errorUrl\\\":\\\"\\\"}");
            }

        } catch (Exception e) {
        }
    }
}
