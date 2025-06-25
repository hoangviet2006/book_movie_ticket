import {logout} from "../../service/userService/UserService";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

const LogoutComponent =()=>{
    const navigate=useNavigate();
    const fetchData = async ()=>{
       await logout();
       navigate('/login')
    }
    fetchData();
}
export default LogoutComponent;