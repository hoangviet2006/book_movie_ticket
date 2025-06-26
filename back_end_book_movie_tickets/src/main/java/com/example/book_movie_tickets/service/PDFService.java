package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.model.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PDFService  {
    public byte[] generateTicketPdf(List<Ticket> tickets) throws Exception{
        ByteArrayOutputStream byteArrayOutputStream  =new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,byteArrayOutputStream);
        document.open();
        String fontPath = "D:\\project_book_movie_tickets\\back_end_book_movie_tickets\\src\\main\\resources\\fonts\\RobotoSlab[wght].ttf";
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle  = new Font(baseFont,16,Font.BOLD, BaseColor.BLUE);
        Font fontNormal = new Font(baseFont,12,Font.NORMAL,BaseColor.BLACK);
        document.add(new Paragraph("Vé xem phim",fontTitle));
        document.add(new Paragraph(""));
        for (Ticket t:tickets){
            Booking booking = t.getBooking();
            Movie movie = booking.getShows().getMovie();
            Shows shows = booking.getShows();
            Cinema cinema = shows.getRoom().getCinema();
            document.add(new Paragraph("Phim: "+movie.getTitle(),fontNormal));
            document.add(new Paragraph("Tên rạp: "+cinema.getName(),fontNormal));
            document.add(new Paragraph("Địa chỉ: "+cinema.getAddress(),fontNormal));
            document.add(new Paragraph("Phòng: "+shows.getRoom().getName(),fontNormal));
            document.add(new Paragraph("Ngày chiếu: "+shows.getDateShow(),fontNormal));
            document.add(new Paragraph("Giờ chiếu: "+shows.getStartTime()+" - "+shows.getEndTime(),fontNormal));
            document.add(new Paragraph("Mã ghế: "+t.getSeat().getSeatCode(),fontNormal));
            document.add(new Paragraph("--------------------------------------------------"));
        }
        document.close();
        System.out.println(document);
        return byteArrayOutputStream.toByteArray();
    }
}
