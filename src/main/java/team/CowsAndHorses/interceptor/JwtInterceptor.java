package team.CowsAndHorses.interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.logging.Log;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import team.CowsAndHorses.util.JwtUtil;

import java.util.Map;
import java.util.logging.Logger;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        String origin = request.getHeader("Origin");
//        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, token");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Max-Age", "3600");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从 http 请求头中取出 token
        String token = request.getHeader("token");
        System.out.println("此处测试是否拿到了token：" + token);
        if (token == null) {
            throw new RuntimeException("无 token ，请重新登陆");
        }
        //验证 token
        JwtUtil.checkSign(token);
        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getUserId(token);
        //获取 token 中的其他数据
        Map<String, Object> info = JwtUtil.getInfo(token);
        info.forEach((k, v) -> System.out.println(k + ":" + v));
        return true;
    }
}