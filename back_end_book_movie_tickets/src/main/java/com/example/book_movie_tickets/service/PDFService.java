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
        Font labelFont = new Font(baseFont, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Font contentFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.BLACK);
        Font lineFont = new Font(baseFont, 10, Font.ITALIC, BaseColor.GRAY);
        for (Ticket t:tickets){
            Booking booking = t.getBooking();
            Movie movie = booking.getShows().getMovie();
            Shows shows = booking.getShows();
            Cinema cinema = shows.getRoom().getCinema();
            Paragraph header = new Paragraph("MOVIEGO TICKET", fontTitle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Phim: ",labelFont));
            document.add(new Paragraph(movie.getTitle(), contentFont));
            document.add(new Paragraph("Rạp: ",labelFont));
            document.add(new Paragraph(cinema.getName(),contentFont));
            document.add(new Paragraph("Địa chỉ: ",labelFont));
            document.add(new Paragraph(cinema.getAddress(),contentFont));
            document.add(new Paragraph("Phòng: ",labelFont));
            document.add(new Paragraph(shows.getRoom().getName(),contentFont));
            document.add(new Paragraph("Ngày chiếu: ",labelFont));
            document.add(new Paragraph(String.valueOf(shows.getDateShow()),contentFont));
            document.add(new Paragraph("Giờ chiếu: ",labelFont));
            document.add(new Paragraph(shows.getStartTime()+" - "+shows.getEndTime(),contentFont));
            document.add(new Paragraph("Mã ghế: ",labelFont));
            document.add(new Paragraph(t.getSeat().getSeatCode(),labelFont));
            String prPath = "D:\\project_book_movie_tickets\\back_end_book_movie_tickets\\src\\main\\resources\\static\\image\\qrcode_221957765_14e540f7cde8568b1619a4dd6d3ea5ea.png";
            Image image = Image.getInstance(prPath);
            image.scaleAbsolute(150,150);
            float imageWidth = 150;
            float centerX=(document.getPageSize().getWidth()-imageWidth)/2;
            float posY =  document.bottom()+70;
            image.setAbsolutePosition(centerX,posY);
            document.add(image);
            Paragraph line = new Paragraph("------------------------------", lineFont);
            line.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(" "));
            document.add(line);
            document.add(new Paragraph(" "));
            if (tickets.indexOf(t) < tickets.size()-1){
                document.newPage();
            }
        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
