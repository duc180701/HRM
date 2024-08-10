package com.training.hrm.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final Map<String, UserRequestInfo> requestMap = new HashMap<>();
    private final SystemRequestInfo systemRequestInfo = new SystemRequestInfo();
    private static final int MAX_REQUESTS_PER_USER = 3;
    private static final long TIME_FRAME_PER_USER = 60 * 1000;
    private static final int MAX_REQUESTS_SYSTEM = 3000;
    private static final long TIME_FRAME_SYSTEM = 60 * 60 * 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long currentTime = Instant.now().toEpochMilli();

        synchronized (systemRequestInfo) {
            List<Long> updatedSystemRequestTimes = new ArrayList<>();
            for (Long time : systemRequestInfo.getRequestTimes()) {
                if (currentTime - time <= TIME_FRAME_SYSTEM) {
                    updatedSystemRequestTimes.add(time);
                }
            }

            if (updatedSystemRequestTimes.size() >= MAX_REQUESTS_SYSTEM) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Too many request for system, please wait to send more request");
                return false;
            }
            updatedSystemRequestTimes.add(currentTime);
            systemRequestInfo.setRequestTimes(updatedSystemRequestTimes);
        }

        if (request.getUserPrincipal() == null) {
            return true;
        }

        String username = request.getUserPrincipal().getName(); // Lấy tên người dùng từ token

        UserRequestInfo userRequestInfo = requestMap.getOrDefault(username, new UserRequestInfo());

        // Xóa các request đã quá thời gian bằng cách tạo một danh sách mới
        List<Long> updatedRequestTimes = new ArrayList<>();
        for (Long time : userRequestInfo.getRequestTimes()) {
            if (currentTime - time <= TIME_FRAME_PER_USER) {
                updatedRequestTimes.add(time);
            }
        }

        // Kiểm tra nếu số lượng request vượt quá giới hạn
        if (userRequestInfo.getRequestTimes().size() >= MAX_REQUESTS_PER_USER) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Too many request for this user, please wait to send more request");
            return false;
        }

        // Thêm thời gian của request hiện tại vào danh sách
        updatedRequestTimes.add(currentTime);

        // Cập nhật lại thông tin của người dùng trong bản đồ
        userRequestInfo.setRequestTimes(updatedRequestTimes);
        requestMap.put(username, userRequestInfo);

        return true; // Cho phép tiếp tục xử lý request
    }

    private static class UserRequestInfo {
        private List<Long> requestTimes = new ArrayList<>();

        public List<Long> getRequestTimes() {
            return requestTimes;
        }

        public void setRequestTimes(List<Long> requestTimes) {
            this.requestTimes = requestTimes;
        }
    }

    private static class SystemRequestInfo {
        private List<Long> requestTimes = new ArrayList<>();

        public List<Long> getRequestTimes() {
            return requestTimes;
        }

        public void setRequestTimes(List<Long> requestTimes) {
            this.requestTimes = requestTimes;
        }
    }
}