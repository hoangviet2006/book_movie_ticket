import {ErrorMessage, Field, Form, Formik} from "formik";
import React, {useEffect, useState} from "react";
import * as Yup from "yup";
import {getAllCinema} from "../../../service/userService/CinemaService";
import '../../../css/admin/room/createRoom.css'
import {createRoom} from "../../../service/adminService/RoomService";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
const CreateRoomComponent=()=>{
    const [cinema,setCinema] = useState([]);
    const navigate=useNavigate();
    useEffect(() => {
        const fetchData = async ()=>{
            const data = await getAllCinema();
            setCinema(data);
        }
        fetchData();
    }, []);
    const validationSchema = Yup.object({
        name: Yup.string().required("Tên rạp không được để trống"),
        seatCount: Yup.number().required("Tổng số ghế của phòng không được để trống").min(1,"Số ghế ít nhất là 1"),
        cinema: Yup.string().required("Phải chọn rạp"),
    });
    const initialValues = {
        name: "",
        seatCount: "",
        cinema: ""
    };
    const handleSubmit = async (value)=>{
        const payload = {
            name: value.name,
            seatCount: value.seatCount,
            cinema: {
                id: parseInt(value.cinema)
            }
        };
        await createRoom(payload);
        navigate('/admin/room');
        toast("Thêm mới thành công!")
    }
    return(
        <div className="create-room-container">
            <div className={'create-room'} style={{width:500,marginTop:200}}>
                <h2 style={{textAlign:"center"}}>Thêm phòng chiếu</h2>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
                    <Form >
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
                                <option value={''}>--- chọn rạp chiếu ---</option>
                                {cinema && cinema.map((c) => (
                                    <option value={c.id}>Rạp: {c.name} -- {c.address}</option>
                                ))}
                            </Field>
                            <ErrorMessage name="cinema" component="div" className="text-danger"/>
                        </div>

                        <button type="submit">Thêm phim</button>
                    </Form>
                </Formik>
            </div>
        </div>
    );
}
export default CreateRoomComponent;