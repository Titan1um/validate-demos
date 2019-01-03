package live.validate.jun.service;

import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

public class Validator {
	private String ts = null;
	private String userid = null;
	private String id = null;
	private String secretkey = null;// 两种验证方式secretkey不同，故有两个init版本
	private String token = null;
	private String sign = null;
	private String nickname = "Ti";
	private String url = null;
	private String marqueeName = "test";
	private String avatar = "http://live.polyv.net/assets/images/avatars/9avatar.jpg";
	private int status = 1;

	public String getSDCallBack(HttpServletRequest req) {
		initSD(req);
		ts = String.valueOf(System.currentTimeMillis());
		//test
		System.out.println("========inside sd========");
		System.out.println("sign:"+sign);
		System.out.println("token:"+token);
		//test
		if (sign.equals(token)) {
			String tmp = secretkey + id + secretkey + ts + secretkey + userid;
			sign = DigestUtils.md5Hex(tmp.getBytes(Charset.forName("UTF-8")));
			String res = url + "?userid=" + userid + "&nickname=" + nickname + "&marqueeName=" + marqueeName
					+ "&avatar=" + avatar + "&ts=" + ts + "&sign=" + sign;
			System.out.println("res:"+res);
			String finalRes = "<script language='javascript' type='text/javascript'>location.href='"+res+"'</script>";
			System.out.println("finalRes:"+finalRes);
			return finalRes;
		}
		System.out.println("error");
		return "error";
	}

	public String getOVCallBack(HttpServletRequest req) {
		initOV(req);
		JSONObject json = new JSONObject();
		//test
		ts = String.valueOf(System.currentTimeMillis());
		System.out.println("========inside sd========");
		System.out.println("sign:"+sign);
		System.out.println("token:"+token);
		//test
		if (!sign.equals(token)) {
			json.put("status", 0);
			json.put("errorUrl", "http://www.baidu.com");
		} else {
			json.put("status", 1);
			json.put("userid", userid);
			json.put("nickname", nickname);
			json.put("avatar", avatar);
			//test
			System.out.println(json);
			return json.toString();
		}
		//test
		System.out.println(json);
		return json.toString();
	}

	public void initOV(HttpServletRequest req) {
		System.out.println("========inside initOV========");
		secretkey = "em31gCSY1h";
		System.out.println("set secretkey:"+secretkey);
		token = req.getParameter("token");
		System.out.println("set token:"+token);
		ts = req.getParameter("ts");
		System.out.println("set ts:"+ts);
		userid = req.getParameter("userid");
		System.out.println("set userid:"+userid);
		String plain = secretkey + userid + secretkey + ts;
		System.out.println("set plain:"+plain);
		sign = DigestUtils.md5Hex(plain.getBytes(Charset.forName("UTF-8")));
		System.out.println("set sign:"+sign);
	}

	public void initSD(HttpServletRequest req) {
		System.out.println("========inside initSD========");
		secretkey = "KWWXssNYBK";
		System.out.println("set secretkey:"+secretkey);
		sign = req.getParameter("sign");
		System.out.println("set sign:"+sign);
		ts = req.getParameter("ts");
		System.out.println("set ts:"+ts);
		id = req.getParameter("id");
		System.out.println("set id:"+id);
		url = req.getParameter("url");
		System.out.println("set url:"+url);
		userid = "eciyhturt8";
		System.out.println("set userid:"+userid);
		String plain = secretkey + id + secretkey + ts;
		System.out.println("set plain:"+plain);
		token = DigestUtils.md5Hex(plain.getBytes(Charset.forName("UTF-8")));
		System.out.println("set token:"+token);
	}
	
	public String execute(ServletRequest request, ServletResponse response) throws Exception {
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
        return null;
    }
}
