import {ErrorMessage, Field, Form, Formik} from "formik";
import DatePicker from "react-datepicker";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {getAllMovie} from "../../../service/userService/MovieService";
import {getAllRoom} from "../../../service/userService/RoomService";
import * as Yup from "yup";
import {detailShow, updateShow} from "../../../service/adminService/ShowService";
import {toast} from "react-toastify";

const UpdateShowComponent = ()=>{
    const [movie,setMovie] = useState([]);
    const [room,setRoom] = useState([]);
    const [show,setShow] = useState([]);
    const navigate = useNavigate();
    const {id} = useParams();
    useEffect(() => {
        try {
            const fetchData = async ()=>{
                const data =await detailShow(id);
                setShow(data)
                const movie = await getAllMovie();
                const room =await getAllRoom();
                setRoom(room)
                setMovie(movie)
            }
            fetchData();
        }catch (e) {
            console.log("Lỗi: "+e)
        }

    }, []);
    const validationSchema = Yup.object({
        dateShow: Yup.date()
            .required("Vui lòng chọn ngày chiếu")
            .typeError("Ngày chiếu không hợp lệ"),

        startTime: Yup.string()
            .required("Vui lòng chọn thời gian bắt đầu"),

        endTime: Yup.string()
            .required("Vui lòng chọn thời gian kết thúc")
            .test("is-after", "Thời gian kết thúc phải sau thời gian bắt đầu", function (value) {
                const { startTime } = this.parent;
                return startTime && value > startTime;
            }),

        movie: Yup.number()
            .typeError("Phim không hợp lệ")
            .required("Vui lòng chọn phim")
            .min(1, "Phim không hợp lệ"),

        room: Yup.number()
            .typeError("Phòng chiếu không hợp lệ")
            .required("Vui lòng chọn phòng chiếu")
            .min(1, "Phòng chiếu không hợp lệ"),
    });
    const initialValues = {
        dateShow: show?.dateShow,
        startTime:show?.startTime,
        endTime: show?.endTime,
        movie:show?.movie?.id?.toString(),
        room:show?.room?.id?.toString()
    };
    const handleSubmit = async (value)=>{
        try {
            const payload = {
                dateShow: value.dateShow,
                startTime: value.startTime,
                endTime: value.endTime,
                movie: {
                    id: parseInt(value.movie)
                },
                room: {
                    id: parseInt(value.room)
                }
            };
            await updateShow(id,payload);
            navigate('/admin/show');
            toast("Chỉnh sửa thành công!")
        }catch (e) {
            toast.warning(e?.response?.data)
        }

    }
    return(
        <>
            <div className="create-show-container">
                <div className={'create-show'} style={{width:500,marginTop:200}}>
                    <h2 style={{textAlign: "center"}}>Chỉnh sửa xuất chiếu</h2>
                    <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit} enableReinitialize={true}>
                        <Form>
                            <div className="form-group">
                                <label htmlFor="dateShow">Ngày chiếu</label>
                                <Field type="date" name="dateShow"/>
                                <ErrorMessage name="dateShow" component="div" className="text-danger"/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="startTime">Thời gian bắt đầu chiếu</label>
                                <Field name="startTime">
                                    {({field, form}) => (
                                        <DatePicker
                                            selected={field.value ? new Date(`2000-01-01T${field.value}`) : null}
                                            onChange={(date) => {
                                                const timeStr = date.toTimeString().slice(0, 5); // "HH:mm"
                                                form.setFieldValue("startTime", timeStr);
                                            }}
                                            showTimeSelect
                                            showTimeSelectOnly
                                            timeIntervals={5} // ✅ Cách nhau 5 phút
                                            timeFormat="HH:mm" // ✅ Hiển thị 24h
                                            timeCaption="Giờ"
                                            dateFormat="HH:mm"
                                            placeholderText="Chọn thời gian"
                                        />
                                    )}
                                </Field>
                                <ErrorMessage name="startTime" component="div" className="text-danger"/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="endTime">Thời gian kết thúc</label>
                                <Field name="endTime">
                                    {({field, form}) => (
                                        <DatePicker
                                            selected={field.value ? new Date(`2000-01-01T${field.value}`) : null}
                                            onChange={(date) => {
                                                const timeStr = date.toTimeString().slice(0, 5); // "HH:mm"
                                                form.setFieldValue("endTime", timeStr);
                                            }}
                                            showTimeSelect
                                            showTimeSelectOnly
                                            timeIntervals={5} // ✅ Cách nhau 5 phút
                                            timeFormat="HH:mm" // ✅ Hiển thị 24h
                                            timeCaption="Giờ"
                                            dateFormat="HH:mm"
                                            placeholderText="Chọn thời gian"
                                        />
                                    )}
                                </Field>
                                <ErrorMessage name="endTime" component="div" className="text-danger"/>
                            </div>
                            <div className="form-group">
                                <Field as={'select'} name={'movie'}>
                                    <option value={''} disabled hidden>--- Chọn phim ---</option>
                                    {movie && movie.map((c) => (
                                        <option value={c.id}>{c.title}</option>
                                    ))}
                                </Field>
                                <ErrorMessage name="movie" component="div" className="text-danger"/>
                            </div>
                            <div className="form-group">
                                <Field as={'select'} name={'room'}>
                                    <option value={''} disabled hidden>--- Chọn phòng chiếu ---</option>
                                    {room && room.map((c) => (
                                        <option value={c.id}>{c.name}</option>
                                    ))}
                                </Field>
                                <ErrorMessage name="room" component="div" className="text-danger"/>
                            </div>

                            <button type="submit">Chỉnh sửa</button>
                        </Form>
                    </Formik>
                </div>
            </div>
        </>
    );
}
export default UpdateShowComponent;