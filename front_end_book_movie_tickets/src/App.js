import {ToastContainer} from "react-toastify";
import './App.css';
import { Routes, Route } from "react-router-dom";
import RegisterComponent from "./component/user/RegisterComponent";
import LoginComponent from "./component/user/LoginComponent";
import LogoutComponent from "./component/user/LogoutComponent";
import ConfirmOtp from "./component/user/ConfirmOtp";
import HomeComponent from "./component/homePage/HomeComponent";
import ForgotPasswordComponent from "./component/forgotPassword/ForgotPasswordComponent";
import Profile from "./component/user/Profile";
import ForgotPasswordOtp from "./component/forgotPassword/ForgotPasswordOtp";
import ConfirmForgotPasswordComponent from "./component/forgotPassword/ConfirmForgotPasswordComponent";
import ResetPasswordComponent from "./component/forgotPassword/ResetPasswordComponent";

import GetAllMovieUserComponent from "./component/user/GetAllMovieUserComponent";
import CreateMovieComponent from "./component/admin/movie/CreateMovieComponent";
import GetAllMovieComponent from "./component/admin/movie/GetAllMovieComponent";
import UpdateMovieComponent from "./component/admin/movie/UpdateMovieComponent";
import GetAllCinemaComponent from "./component/admin/cinema/GetAllCinemaComponent";
import CreateCinemaComponent from "./component/admin/cinema/CreateCinemaComponent";
import UpdateCinemaComponent from "./component/admin/cinema/UpdateCinemaComponent";
import GetAllRoomComponent from "./component/admin/room/GetAllRoomComponent";
import DetailRoomComponent from "./component/admin/room/DetailRoomComponent";
import AdminLayout from "./component/layout/AdminLayout";
import DetailShowByMovieId from "./component/user/DetailMovie";
import CreateRoomComponent from "./component/admin/room/CreateRoomComponent";
import UpdateRoomComponent from "./component/admin/room/UpdateRoomComponent";
import GetAllShowComponent from "./component/admin/show/GetAllShowComponent";
import CreateShowComponent from "./component/admin/show/CreateShowComponent";
import UpdateShowComponent from "./component/admin/show/UpdateShowComponent";
import BookingTicketComponent from "./component/booking/BookingTicketComponent";
import ConfirmBookingTicket from "./component/booking/ConfirmBookingTicket";
import ViewBookingComponent from "./component/booking/ViewBookingComponent";
import PaymentVNPayComponent from "./component/booking/PaymentVNPayComponent";
import TicketBookingHistoryComponent from "./component/user/TicketBookingHistoryComponent";
function App() {
  return (
    <>
      <ToastContainer/>

      <Routes>
        <Route path="/admin" element={<AdminLayout />}>
          <Route path="movie" element={<GetAllMovieComponent />} />
          <Route path="movie/create" element={<CreateMovieComponent />} />
          <Route path="movie/update/:id" element={<UpdateMovieComponent />} />
          <Route path="cinema" element={<GetAllCinemaComponent />} />
          <Route path="cinema/create" element={<CreateCinemaComponent />} />
          <Route path="cinema/update/:id" element={<UpdateCinemaComponent />} />
          <Route path="room" element={<GetAllRoomComponent />} />
          <Route path="room/create" element={<CreateRoomComponent />} />
          <Route path="room/update/:id" element={<UpdateRoomComponent />} />
          <Route path="room/detail/:id" element={<DetailRoomComponent />} />
          <Route path="show" element={<GetAllShowComponent />} />
          <Route path="show/update/:id" element={<UpdateShowComponent />} />
          <Route path="show/create" element={<CreateShowComponent />} />
        </Route>
        <Route path={"/movie"} element={<GetAllMovieUserComponent/>}></Route>
        <Route path={"/movie/:id/detail"} element={<DetailShowByMovieId/>}></Route>
        <Route path={"/register"} element={<RegisterComponent/>}></Route>
        <Route path={"/login"} element={<LoginComponent/>}></Route>
        <Route path={"/logout"} element={<LogoutComponent/>}></Route>
        <Route path={"/"} element={<HomeComponent/>}></Route>
        <Route path={"/profile"} element={<Profile/>}></Route>
        <Route path={"/confirmOtp"} element={<ConfirmOtp/>}></Route>
        <Route path={"/forgot-password"} element={<ForgotPasswordComponent/>}></Route>
        <Route path={"/forgot-password-otp"} element={<ForgotPasswordOtp/>}></Route>
        <Route path={"/update-password"} element={<ConfirmForgotPasswordComponent/>}></Route>
        <Route path={"/reset-password"} element={<ResetPasswordComponent/>}></Route>
        <Route path={"/booking/:id"} element={<BookingTicketComponent/>}></Route>
        <Route path={"/confirm-booking"} element={<ConfirmBookingTicket/>}></Route>
        <Route path={"/booking-view"} element={<ViewBookingComponent/>}></Route>
        <Route path={"/payment-vnpay"} element={<PaymentVNPayComponent/>}></Route>
        <Route path={"/user/booking/history"} element={<TicketBookingHistoryComponent/>}></Route>

      </Routes>
    </>
  );
}

export default App;
