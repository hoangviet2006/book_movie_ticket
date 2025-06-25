import React from 'react';
import { Facebook, Instagram, Youtube, Mail, Phone, MapPin, Smartphone, ExternalLink } from 'lucide-react';
import '../../css/home/footer.css';

const FooterComponent = () => {
    return (
        <footer className="footer">
            <div className="footer-container">
                <div className="footer-column">
                    <h4>🎬 MovieGo.vn</h4>
                    <p>Hệ thống đặt vé xem phim hiện đại, hỗ trợ nhiều cụm rạp, tiện lợi và nhanh chóng. Trải nghiệm
                        tuyệt vời cho mọi khán giả yêu điện ảnh.</p>
                </div>

                <div className="footer-column">
                    <h4>📌 Liên hệ</h4>
                    <p><MapPin size={16}/> 295 Đường Nguyễn Tất Thành, TP Đà Nẵng</p>
                    <p><Phone size={16}/>0329600594</p>
                    <p><Mail size={16}/> hoangviet30032006@gmail.com</p>
                </div>

                <div className="footer-column">
                    <h4>🔗 Liên kết nhanh</h4>
                    <ul className="footer-links">
                        <li><ExternalLink size={14}/> <a href="/movie">Phim đang chiếu</a></li>
                        <li><ExternalLink size={14}/> <a href="/cinema">Hệ thống rạp</a></li>
                        <li><ExternalLink size={14}/> <a href="#">Câu hỏi thường gặp</a></li>
                        <li><ExternalLink size={14}/> <a href="#">Chính sách bảo mật</a></li>
                    </ul>
                </div>

                <div className="footer-column">
                    <h4>📱 Tải ứng dụng</h4>
                    <p><Smartphone size={16}/> MovieGo App cho Android & iOS</p>
                    <div className="app-links">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg"
                             alt="Google Play"/>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/6/67/App_Store_%28iOS%29.svg"
                             alt="App Store"/>
                    </div>
                    <h4>Kết nối với chúng tôi</h4>
                    <div className="social-icons">
                        <a href="#"><Facebook size={20}/></a>
                        <a href="#"><Instagram size={20}/></a>
                        <a href="#"><Youtube size={20}/></a>
                    </div>
                </div>
                <div className="footer-column footer-map">
                    <h4>📍 Bản đồ rạp</h4>
                    <div className="map-container">
                        <iframe
                            title="Địa chỉ rạp"
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15335.180660761243!2d108.20042839999999!3d16.07611575!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31421846431fd0fd%3A0x780138ba60fa3aa6!2zMjk1IE5ndXnhu4VuIFThuqV0IFRow6BuaCwgVGhhbmggQsOsbmgsIEjhuqNpIENow6J1LCDEkMOgIE7hurVuZyA1NTAwMDAsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1750143863479!5m2!1svi!2s"
                            width="100%"
                            height="200"
                            style={{border: 0}}
                            allowFullScreen=""
                            loading="lazy"
                            referrerPolicy="no-referrer-when-downgrade"
                        ></iframe>
                    </div>
                </div>
            </div>

            <div className="footer-bottom">
                © {new Date().getFullYear()} MyCinema. Thiết kế & phát triển bởi Nhóm Dev chuyên nghiệp.
            </div>

        </footer>
    );
};

export default FooterComponent;
