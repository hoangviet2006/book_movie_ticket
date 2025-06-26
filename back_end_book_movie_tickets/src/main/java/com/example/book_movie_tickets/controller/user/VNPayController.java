package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.config.ConfigVNPay;
import com.example.book_movie_tickets.dto.PaymentResponse;
import com.example.book_movie_tickets.model.Booking;
import com.example.book_movie_tickets.model.Ticket;
import com.example.book_movie_tickets.repository.IBookingRepository;
import com.example.book_movie_tickets.repository.ITicketRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment/vnpay")
public class VNPayController {
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IBookingRepository bookingRepository;
    @PostMapping("")
    public ResponseEntity<?> createPayment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            System.out.println(">>> [VNPAY] Gọi API với thông tin:");
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
            String orderType = req.getParameter("ordertype");
            String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
            String vnp_IpAddr = ConfigVNPay.getIpAddress(req);
            String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;
            String ticketIdsStr = req.getParameter("ticketIds");
            List<Integer> ticketIds = Arrays.stream(ticketIdsStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            List<Ticket> tickets = ticketRepository.findAllById(ticketIds);
            for (Ticket t : tickets) {
                t.setTxnRef(vnp_TxnRef);
            }
            ticketRepository.saveAll(tickets);
            String amountStr = req.getParameter("amount");
            if (amountStr == null || amountStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Thiếu tham số amount");
            }
            int amount = Integer.parseInt(amountStr) * 100;
            Map vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            String bank_code = req.getParameter("bankcode");
            if (bank_code != null && !bank_code.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bank_code);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = req.getParameter("language");
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            System.out.println("🔁 vnp_ReturnUrl đang sử dụng là: " + ConfigVNPay.vnp_Returnurl);
            vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_Returnurl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            //Add Params of 2.1.0 Version
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
            //Billing
            vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
            vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
            String fullName = req.getParameter("txt_billing_fullname");
            if (fullName != null && !fullName.trim().isEmpty()) {
                fullName = fullName.trim();
                if (fullName.contains(" ")) {
                    int idx = fullName.indexOf(' ');
                    if (idx != -1) {
                        String firstName = fullName.substring(0, idx);
                        String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
                        vnp_Params.put("vnp_Bill_FirstName", firstName);
                        vnp_Params.put("vnp_Bill_LastName", lastName);
                    } else {
                        vnp_Params.put("vnp_Bill_FirstName", fullName);
                        vnp_Params.put("vnp_Bill_LastName", fullName);
                    }
                } else {
                    vnp_Params.put("vnp_Bill_FirstName", fullName);
                    vnp_Params.put("vnp_Bill_LastName", fullName);
                }
            }

            vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
            vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
            vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
            if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
                vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
            }
            // Invoice
            vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
            vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
            vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
            vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
            vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
            vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
            vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
            //Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;
            com.google.gson.JsonObject job = new com.google.gson.JsonObject();
            job.addProperty("code", "00");
            job.addProperty("message", "success");
            job.addProperty("data", paymentUrl);
            ;
            PaymentResponse response = new PaymentResponse("00", "success", paymentUrl);
            System.out.println("🎯 ticketIdsStr =----------- " + req.getParameter("ticketIds"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
            JsonObject error = new JsonObject();
            error.addProperty("code", "99");
            error.addProperty("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @GetMapping("/vnpay-return")
    public void vnpayReturn(@RequestParam Map<String, String> allParams, HttpServletResponse response) throws Exception {
        String secureHash = allParams.remove("vnp_SecureHash"); // Lấy và loại bỏ hash khỏi map
        String hashData = ConfigVNPay.hashAllFields(allParams); // Tạo lại chuỗi hash
        String myHash = ConfigVNPay.hmacSHA512(ConfigVNPay.vnp_HashSecret, hashData);
        String message;
        String txnRef = allParams.get("vnp_TxnRef");

        if ("00".equals(allParams.get("vnp_ResponseCode"))) {
            List<Ticket> tickets = ticketRepository.findByTxnRef(txnRef);
            if (tickets.isEmpty()){
                response.sendRedirect("http://localhost:3000?status=fail&message=+" +
                                      URLEncoder.encode("Không tìm thấy vé",StandardCharsets.UTF_8));
                return;
            }
            Booking booking = bookingRepository.findById(tickets.get(0).getBooking().getId());
            if (booking!=null){
                booking.setStatus(Booking.Status.PAID);
                bookingRepository.save(booking);
            }
            for (Ticket t : tickets) {
                t.setStatus(Ticket.Status.PAID);
            }
            ticketRepository.saveAll(tickets);
            message = "Thanh toán thành công";
            response.sendRedirect("http://localhost:3000?status=success&txnRef=" + txnRef +
                                  "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
        } else {
            message = "Thanh toán thất bại: Mã " + allParams.get("vnp_ResponseCode");
            response.sendRedirect("http://localhost:3000?status=fail&txnRef=" + txnRef +
                                  "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
        }
    }
}
