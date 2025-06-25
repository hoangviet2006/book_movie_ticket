import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {getAllCinema} from "../../../service/userService/CinemaService";
import * as Yup from "yup";
import {createRoom, deleteRoom, detailRoom, updateRoom} from "../../../service/adminService/RoomService";
import {toast} from "react-toastify";
import {ErrorMessage, Field, Form, Formik} from "formik";


const UpdateRoomComponent = () => {
    const [cinema,setCinema] = useState([]);
    const [room,setRoom] = useState([]);
    const {id} = useParams();
    const navigate=useNavigate();
    useEffect(() => {
        const fetchData = async ()=>{
            const data = await detailRoom(id);
            const cinema = await getAllCinema();
            setCinema(cinema);
            console.log(data.room)
            setRoom(data.room);
        }
        fetchData();
    }, []);
    const validationSchema = Yup.object({
        name: Yup.string().required("Tên rạp không được để trống"),
        seatCount: Yup.number().required("Tổng số ghế của phòng không được để trống").min(1,"Số ghế ít nhất là 1"),
        cinema: Yup.string().required("Phải chọn rạp"),
    });
    const initialValues = {
        name: room?.name,
        seatCount: room?.seatCount,
        cinema: room?.cinema?.id?.toString()
    };
    const handleSubmit = async (value)=>{
        const payload = {
            name: value.name,
            seatCount: value.seatCount,
            cinema: {
                id: parseInt(value.cinema)
            }
        };
        await updateRoom(id,payload);
        navigate('/admin/room');
        toast("Chỉnh sửa thành công!")

    }
    return(
        <div className="create-room-container">
            <div className={'create-room'} style={{width:500,marginTop:200}}>
                <h2 style={{textAlign:"center"}}>Chỉnh sửa phòng chiếu</h2>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit} enableReinitialize={true}>
                    <Form>
                        <div className="form-group">
                            <label htmlFor="name">Tên phòng chiếu</label>
                            <Field type="text" name="name"/>
                            <ErrorMessage name="name" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="seatCount">Tổng số ghế ngồi</label>
                            <Field name="seatCount"/>
                            <ErrorMessage name="seatCount" component="div" className="text-danger"/>
                        </div>

                        <div className="form-group">
                            <Field as={'select'} name={'cinema'}>
                                <option value="" disabled hidden>-- Chọn rạp chiếu --</option>
                                {cinema && cinema.map((c) => (
                                    <option value={c.id}>Rạp: {c.name} -- {c.address}</option>
                                ))}
                            </Field>
                            <ErrorMessage name="cinema" component="div" className="text-danger"/>
                        </div>

                        <button type="submit">Chỉnh sửa</button>
                    </Form>
                </Formik>
            </div>
        </div>
    );
}
export default UpdateRoomComponent;