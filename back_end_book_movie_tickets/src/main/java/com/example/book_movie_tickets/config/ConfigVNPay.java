package com.example.book_movie_tickets.config;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConfigVNPay {
    public static String vnp_TmnCode = "P05OHVKY"; // Mã định danh website  với VNPay do VNPay cung cấp
    public static String vnp_HashSecret = "JL2M9BBVJ1POMMHDI8B15ZHOJQ8NE50T"; // Mã bí mật để tạo chữ ký bảo mật VNPay cung cấp
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";// URL thanh toán của VNPay
    public static String vnp_Returnurl = "http://10.10.8.24:8080/api/payment/vnpay/vnpay-return"; //URL mà VNPay sẽ gọi lại sau khi thanh toán xong.

    public static String getRandomNumber(int len) {  // hàm tạo mã giao dịch ngẫu nhiên
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < len) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String getIpAddress(HttpServletRequest request) { // hàm lấy ip của người dùng
        String ip = request.getHeader("X-FORWARDED-FOR");
        return (ip == null) ? request.getRemoteAddr() : ip;
    }

    public static String hmacSHA512(String key, String data) throws Exception { // hàm tạo mã bí mật
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
        hmac512.init(secretKey);
        byte[] bytes = hmac512.doFinal(data.getBytes("UTF-8"));
        return javax.xml.bind.DatatypeConverter.printHexBinary(bytes).toUpperCase();
    }
    public static String hashAllFields(Map<String, String> fields) throws Exception {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = fields.get(key);
            if ((value != null) && (value.length() > 0)) {
                sb.append(key);
                sb.append('=');
                sb.append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                if (i != fieldNames.size() - 1) {
                    sb.append('&');
                }
            }
        }
        return sb.toString();
    }
}
